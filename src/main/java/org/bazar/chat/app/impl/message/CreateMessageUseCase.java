package org.bazar.chat.app.impl.message;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.chat.ChatRepository;
import org.bazar.chat.app.api.exception.BusinessException;
import org.bazar.chat.app.api.exception.ErrorCode;
import org.bazar.chat.app.api.message.CreateMessageInbound;
import org.bazar.chat.app.api.message.MessageEventsService;
import org.bazar.chat.app.api.message.MessageRepository;
import org.bazar.chat.app.api.message.dto.AuthorStatus;
import org.bazar.chat.app.api.message.dto.CreateMessageDto;
import org.bazar.chat.app.api.message.dto.ReplyMessageDto;
import org.bazar.chat.app.api.message.dto.event.MessageCreatedEvent;
import org.bazar.chat.app.api.persona.model.UserDto;
import org.bazar.chat.app.api.space.SpaceService;
import org.bazar.chat.app.api.space.dto.SpaceUserDto;
import org.bazar.chat.app.service.AuthorizationService;
import org.bazar.chat.app.service.message.MessageAllowedActionsResolver;
import org.bazar.chat.app.service.message.ReplyMessageCollector;
import org.bazar.chat.app.service.user.UserLoader;
import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.domain.message.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса для создания сообщения в чате
 */
@Component
@RequiredArgsConstructor
public class CreateMessageUseCase implements CreateMessageInbound {
    private final ChatRepository chatRepository;
    private final AuthorizationService authorizationService;
    private final MessageMapper messageMapper;
    private final UserLoader userLoader;
    private final MessageRepository messageRepository;
    private final ReplyMessageCollector replyMessageCollector;
    private final MessageEventsService messageEventsService;
    private final MessageAllowedActionsResolver messageAllowedActionsResolver;
    private final SpaceService spaceService;

    @Override
    @Transactional
    public void execute(CreateMessageDto dto) {
        Chat chat = chatRepository.findByChatId(dto.chatId()).orElseThrow(() -> new BusinessException(ErrorCode.CHAT_BY_ID_NOT_FOUND, dto.chatId()));
        UUID userId = authorizationService.getAuthenticatedUserId();
        Message message = messageMapper.toMessage(dto, getReplyMessageIfExists(dto.chatId(), dto.replyMessageId()), chat, userId);
        messageRepository.save(message);
        Map<UUID, UserDto> usersMap = userLoader.loadUsersForMessages(List.of(message));
        UserDto user = usersMap.get(message.getUserId());
        AuthorStatus authorStatus = AuthorStatus.from(user);
        ReplyMessageDto reply = replyMessageCollector.getReplyMessageDto(message, usersMap);
        MessageCreatedEvent messageCreatedEvent = messageMapper.toMessageCreatedEvent(
                message,
                user,
                authorStatus,
                reply,
                messageAllowedActionsResolver.getAllowedActions(message),
                getChatMembersUuids(chat));
        messageEventsService.publishEvent(messageCreatedEvent);
    }

    // =================================================================================================================
    // = Implementation
    // =================================================================================================================

    private Message getReplyMessageIfExists(Long chatId, Long replyMessageId) {
        if (replyMessageId == null) {
            return null;
        }

        return messageRepository.findByIdAndChatId(replyMessageId, chatId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MESSAGE_NOT_FOUND, replyMessageId));
    }

    // Как будто имеет смысл всё-таки хранить ещё и на стороне bazar-chat пользователей. Вдруг в будущем захотим сделать несколько чатов в рамках спейса
    // Ну и каждый раз ходить за пользователями чата в bazar-space не очень круто, как по мне
    private Set<UUID> getChatMembersUuids(Chat chat) {
        Set<UUID> chatUserIds = spaceService.getUsersBySpaceId(chat.getSpaceId()).stream()
                .map(SpaceUserDto::userId)
                .collect(Collectors.toSet());
        chatUserIds.remove(authorizationService.getAuthenticatedUserId());

        return chatUserIds;
    }
}
