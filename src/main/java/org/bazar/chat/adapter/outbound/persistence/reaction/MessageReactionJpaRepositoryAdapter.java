package org.bazar.chat.adapter.outbound.persistence.reaction;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.reaction.MessageReactionRepository;
import org.bazar.chat.domain.reaction.MessageReaction;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Имплементация репозитория для работы с сущностью реакции на сообщение
 */
@Component
@RequiredArgsConstructor
public class MessageReactionJpaRepositoryAdapter implements MessageReactionRepository {
    private final MessageReactionJpaRepository messageReactionJpaRepository;

    @Override
    public boolean existsUserMessageReaction(Long messageId, Long reactionId, UUID userId) {
        return messageReactionJpaRepository.existsMessageReactionByMessageIdAndReactionIdAndUserId(
                messageId, reactionId, userId);
    }

    @Override
    public void deleteUserMessageReaction(Long messageId, Long reactionId, UUID userId) {
        messageReactionJpaRepository.deleteMessageReaction(messageId, reactionId, userId);
    }

    @Override
    public long countUserMessageReactions(Long messageId, UUID userId) {
        return messageReactionJpaRepository.countMessageReactionsByMessageIdAndUserId(messageId, userId);
    }

    @Override
    public long countMessageReactions(Long messageId, Long reactionId) {
        return messageReactionJpaRepository.countMessageReactionsByMessageIdAndReactionId(messageId, reactionId);
    }

    @Override
    public void save(MessageReaction messageReaction) {
        messageReactionJpaRepository.save(messageReaction);
    }
}
