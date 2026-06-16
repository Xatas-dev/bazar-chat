package org.bazar.chat.app.impl.reaction;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bazar.chat.app.api.message.MessageEventsService;
import org.bazar.chat.app.api.message.MessageRepository;
import org.bazar.chat.app.api.message.dto.AuthorDto;
import org.bazar.chat.app.api.message.dto.AuthorStatus;
import org.bazar.chat.app.api.message.dto.event.ReactionChangedEvent;
import org.bazar.chat.app.api.persona.model.UserDto;
import org.bazar.chat.app.api.reaction.MessageReactionRepository;
import org.bazar.chat.app.api.reaction.ReactionRepository;
import org.bazar.chat.app.api.reaction.UpdateMessageReactionInbound;
import org.bazar.chat.app.api.reaction.dto.UpdatedReactionDto;
import org.bazar.chat.app.api.reaction.dto.UpdatedReactionsDto;
import org.bazar.chat.app.impl.message.MessageMapper;
import org.bazar.chat.app.service.AuthorizationService;
import org.bazar.chat.app.service.user.UserLoader;
import org.bazar.chat.domain.reaction.MessageReaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Реализация интерфейса для изменения реакции на сообщение
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateMessageReactionUseCase implements UpdateMessageReactionInbound {
    private final MessageReactionRepository messageReactionRepository;
    private final MessageRepository messageRepository;
    private final ReactionRepository reactionRepository;
    private final MessageEventsService messageEventsService;
    private final UserLoader userLoader;
    private final MessageMapper messageMapper;
    private final AuthorizationService authorizationService;

    // Возможен race condition при параллельной постановке реакций, из-за чего пользователь в редких случаях может превысить лимит реакций.
    // Сознательно не используем pessimistic locking или подобное, так как реакции не являются критичной частью системы
    // TODO: добавить валидации на постановку реакций через auth (для этого тут как раз временно неиспользуемый chatId): https://grinbog015.atlassian.net/browse/BZR-142
    @Override
    @Transactional
    public UpdatedReactionsDto execute(Long chatId, Long messageId, Long reactionId) {
        UUID userId = authorizationService.getAuthenticatedUserId();

        boolean alreadyExists = messageReactionRepository.existsUserMessageReaction(messageId, reactionId, userId);

        List<UpdatedReactionDto> updatedReactions = new ArrayList<>();
        if (alreadyExists) {
            UpdatedReactionDto removedReaction = removeMessageReaction(chatId, messageId,  reactionId, userId);
            updatedReactions.add(removedReaction);
        } else {
            long userReactionCount = messageReactionRepository.countUserMessageReactions(messageId, userId);
            if (userReactionCount >= 3) {
                Optional<MessageReaction> optional = messageReactionRepository.findOldestUserMessageReaction(messageId, userId);
                if (optional.isEmpty()) {
                    log.error("Oldest message reaction not found for messageId {}, userId {}", messageId, userId);
                    return new UpdatedReactionsDto(messageId, updatedReactions);
                }
                MessageReaction oldestMessageReaction = optional.get();
                log.debug("User reached reaction limit. Deleting oldest reaction: {}", oldestMessageReaction.getReaction().getId());
                UpdatedReactionDto removedReaction = removeMessageReaction(chatId, messageId, oldestMessageReaction.getReaction().getId(), userId);
                updatedReactions.add(removedReaction);
            }

            UpdatedReactionDto addedReaction = addMessageReaction(chatId, messageId, reactionId, userId);
            updatedReactions.add(addedReaction);
        }
        return new UpdatedReactionsDto(messageId, updatedReactions);
    }

    // =================================================================================================================
    // = Implementation
    // =================================================================================================================

    private UpdatedReactionDto addMessageReaction(Long chatId, Long messageId, Long reactionId, UUID userId) {
        messageReactionRepository.save(createMessageReaction(messageId, reactionId, userId));
        log.debug("Added reaction {} to message {}", reactionId, messageId);
        return updateReaction(messageId, reactionId, chatId, true, userId);
    }

    private UpdatedReactionDto removeMessageReaction(Long chatId, Long messageId, Long reactionId, UUID userId) {
        messageReactionRepository.deleteUserMessageReaction(messageId, reactionId, userId);
        log.debug("Removed reaction {} from message {}", reactionId, messageId);
        return updateReaction(messageId, reactionId, chatId, false, userId);
    }

    private UpdatedReactionDto updateReaction(Long messageId, Long reactionId, Long chatId, boolean added, UUID userId) {
        long reactionCount = messageReactionRepository.countMessageReactions(messageId, reactionId);
        messageEventsService.publishEvent(
                createReactionEvent(chatId, messageId, reactionId, reactionCount, added, userId));
        return new  UpdatedReactionDto(reactionId, reactionCount);
    }

    private MessageReaction createMessageReaction(Long messageId, Long reactionId, UUID userId) {
        MessageReaction messageReaction = new MessageReaction();

        messageReaction.setUserId(userId);
        messageReaction.setMessage(messageRepository.getReference(messageId));
        messageReaction.setReaction(reactionRepository.getReference(reactionId));

        return messageReaction;
    }

    private ReactionChangedEvent createReactionEvent(Long chatId, Long messageId, Long reactionId, long count, boolean added, UUID userId) {
        AuthorDto author = getAuthor(userId);
        return new ReactionChangedEvent(
                String.valueOf(chatId),
                String.valueOf(messageId),
                String.valueOf(reactionId),
                count,
                added,
                author
        );
    }

    private AuthorDto getAuthor(UUID userId) {
        UserDto user = userLoader.getUserById(userId).orElse(null);
        return messageMapper.toAuthorDto(user, AuthorStatus.from(user), userId);
    }
}
