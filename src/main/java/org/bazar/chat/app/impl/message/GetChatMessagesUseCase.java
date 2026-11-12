package org.bazar.chat.app.impl.message;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.auth.AuthenticationService;
import org.bazar.chat.app.api.chat.ChatRepository;
import org.bazar.chat.app.api.exception.BusinessException;
import org.bazar.chat.app.api.exception.ErrorCode;
import org.bazar.chat.app.api.message.GetChatMessagesInbound;
import org.bazar.chat.app.api.message.MessageRepository;
import org.bazar.chat.app.api.message.dto.AuthorStatus;
import org.bazar.chat.app.api.message.dto.GetMessageDto;
import org.bazar.chat.app.api.message.dto.MessageReactionDto;
import org.bazar.chat.app.api.message.dto.ReplyMessageDto;
import org.bazar.chat.app.api.persona.model.UserDto;
import org.bazar.chat.app.api.reaction.MessageReactionRepository;
import org.bazar.chat.app.impl.service.encryption.ChatEncryptionService;
import org.bazar.chat.app.impl.service.message.MessageAllowedActionsResolver;
import org.bazar.chat.app.impl.service.message.ReplyMessageCollector;
import org.bazar.chat.app.impl.service.user.UserLoader;
import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.domain.message.Message;
import org.bazar.chat.domain.reaction.MessageReaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса для получения сообщений в чате
 */
@Component
@RequiredArgsConstructor
public class GetChatMessagesUseCase implements GetChatMessagesInbound {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final MessageReactionRepository messageReactionRepository;
    private final MessageMapper messageMapper;
    private final UserLoader userLoader;
    private final ReplyMessageCollector replyMessageCollector;
    private final MessageAllowedActionsResolver messageAllowedActionsResolver;
    private final AuthenticationService authenticationService;
    private final ChatEncryptionService chatEncryptionService;

    @Override
    public Page<GetMessageDto> execute(Long chatId, Pageable pageable) {
        Page<Message> messages = messageRepository.findAllVisibleByChatId(chatId, pageable);
        Map<UUID, UserDto> usersMap = userLoader.loadUsersForMessages(messages.getContent());
        SecretKey dek = getDek(chatId);
        return messages.map(message -> {
                    UserDto user = usersMap.get(message.getUserId());
                    AuthorStatus authorStatus = AuthorStatus.from(user);
                    ReplyMessageDto reply = replyMessageCollector.getReplyMessageDto(message, usersMap, dek);
                    List<MessageReactionDto> messageReactions = getMessageReactions(message.getId());
                    String content = chatEncryptionService.decrypt(message, dek);

                    return messageMapper.toGetMessageDto(
                            message,
                            content,
                            messageAllowedActionsResolver.getAllowedActions(message),
                            user,
                            authorStatus,
                            reply,
                            messageReactions
                    );
                }
        );
    }

    // =================================================================================================================
    // = Implementation
    // =================================================================================================================

    /**
     * Пока сделал логику получения count и reactedByCurrentUser через списки, но в будущем можно сделать отдельные, так
     * как пока что реакций у нас будет мало и такое по идее должно работать быстрее, чем отдельные запросы в БД, но в
     * будущем можно будет переделать
     */
    // TODO: сейчас тут N+1 проблема при поиске реакций. Ищем список реакций для каждого сообщения страницы. Нужно исправить
    private List<MessageReactionDto> getMessageReactions(Long messageId) {
        List<MessageReaction> messageReactions = messageReactionRepository.findAllByMessageId(messageId);
        UUID currentUserId = authenticationService.getAuthenticatedUserId();
        return messageReactions.stream()
                .collect(Collectors.groupingBy(r -> r.getReaction().getId()))
                .entrySet().stream()
                .map(entry -> {
                    List<MessageReaction> reactionGroup = entry.getValue();
                    boolean reactedByCurrentUser = reactionGroup.stream()
                            .anyMatch(r -> currentUserId.equals(r.getUserId()));

                    return new MessageReactionDto(
                            entry.getKey().toString(),
                            reactionGroup.size(),
                            reactedByCurrentUser
                    );
                })
                .toList();
    }

    private SecretKey getDek(Long chatId) {
        Chat chat = chatRepository.findByChatId(chatId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CHAT_BY_ID_NOT_FOUND, chatId));
        return chatEncryptionService.getDek(chat);
    }
}
