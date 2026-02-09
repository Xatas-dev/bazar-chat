package org.bazar.chat.app.impl.message;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.persona.PersonaService;
import org.bazar.chat.app.api.persona.model.UserDto;
import org.bazar.chat.app.api.chat.ChatRepository;
import org.bazar.chat.app.api.exception.BusinessException;
import org.bazar.chat.app.api.exception.ErrorCode;
import org.bazar.chat.app.api.message.MessageEventsService;
import org.bazar.chat.app.api.message.MessageRepository;
import org.bazar.chat.app.api.message.MessageService;
import org.bazar.chat.app.api.message.dto.AuthorStatus;
import org.bazar.chat.app.api.message.dto.CreateMessageDto;
import org.bazar.chat.app.api.message.dto.GetMessageDto;
import org.bazar.chat.app.api.message.dto.GetMessagePageDto;
import org.bazar.chat.app.impl.helpers.SecurityContextHelper;
import org.bazar.chat.app.impl.mapper.PageDtoMapper;
import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.domain.message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Имплементация сервиса для работы со сценариями по сущности Сообщение
 */
@Component
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final MessageMapper mapper;
    private final SecurityContextHelper securityContextHelper;
    private final MessageEventsService messageEventsService;
    private final PageDtoMapper pageDtoMapper;
    private final PersonaService personaService;

    @Override
    public GetMessagePageDto getChatMessages(Long chatId, Pageable pageable) {
        Page<Message> messages = messageRepository.findAllVisibleByChatId(chatId, pageable);
        Map<UUID, UserDto> usersMap = loadUsers(messages);
        Page<GetMessageDto> dtoPage = messages.map(message -> {
                    UserDto user = usersMap.get(message.getUserId());
                    AuthorStatus authorStatus = getAuthorStatus(user);

                    return mapper.toGetMessageDto(
                            message,
                            isDeletableByCurrentUser(message),
                            user,
                            authorStatus
                    );
                }
        );
        return pageDtoMapper.toGetMessagePageDto(dtoPage);
    }

    @Override
    @Transactional
    public void createMessage(CreateMessageDto dto) {
        Chat chat = chatRepository.findByChatId(dto.chatId());
        Message message = mapper.toMessage(dto);
        message.setChat(chat);
        UUID userId = securityContextHelper.getAuthenticatedUserId();
        message.setUserId(userId);
        messageRepository.save(message);
        UserDto user = personaService.getUsersByIds(List.of(userId)).getFirst();
        messageEventsService.publishEvent(mapper.toMessageCreatedEvent(message, user, AuthorStatus.EXIST));
    }

    @Override
    @Transactional
    public void deleteMessages(Long chatId, List<Long> messageIds) {
        List<Message> messagesToDelete = messageRepository.findAllByChatIdAndMessageIds(chatId, messageIds);
        checkMessagesForDeletingByCurrentUser(messagesToDelete);
        messagesToDelete.forEach(message -> message.setVisible(false));
        messageEventsService.publishEvent(mapper.toMessageDeletedEvent(chatId, messageIds));
    }

    @Override
    @Transactional
    public void deleteExpiredMessages() {
        Instant threshold = Instant.now().minus(7, ChronoUnit.DAYS);
        messageRepository.deleteInvisibleMessagesByUpdatedAt(threshold);
    }

    // =================================================================================================================
    // = Implementation
    // =================================================================================================================

    private boolean isDeletableByCurrentUser(Message message) {
        UUID currentUserId = securityContextHelper.getAuthenticatedUserId();
        return currentUserId.equals(message.getUserId());
    }

    private void checkMessagesForDeletingByCurrentUser(List<Message> messages) {
        messages.stream()
                .filter(message -> !isDeletableByCurrentUser(message))
                .findFirst()
                .ifPresent(message -> {
                    throw new BusinessException(ErrorCode.DELETE_MESSAGE_BY_CURRENT_USER_FORBIDDEN, message.getId());
                });
    }

    private Map<UUID, UserDto> loadUsers(Page<Message> messages) {
        List<UUID> userIds = messages.stream()
                .map(Message::getUserId)
                .distinct()
                .toList();
        List<UserDto> usersByIds = personaService.getUsersByIds(userIds);

        return usersByIds.stream()
                .collect(Collectors.toMap(UserDto::userId, Function.identity()));
    }

    private AuthorStatus getAuthorStatus(UserDto user) {
        return user != null ? AuthorStatus.EXIST : AuthorStatus.UNKNOWN;
    }
}
