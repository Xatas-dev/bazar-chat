package org.bazar.chat.app.impl.message;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.exception.BusinessException;
import org.bazar.chat.app.api.exception.ErrorCode;
import org.bazar.chat.app.api.message.EditMessageContentInbound;
import org.bazar.chat.app.api.message.MessageEventsService;
import org.bazar.chat.app.api.message.MessageRepository;
import org.bazar.chat.app.api.message.dto.UpdateMessageDto;
import org.bazar.chat.app.api.auth.AuthenticationService;
import org.bazar.chat.domain.message.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Реализация интерфейса для обновления содержимого сообщения
 */
@Component
@RequiredArgsConstructor
public class EditMessageContentUseCase implements EditMessageContentInbound {
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final AuthenticationService authenticationService;
    private final MessageEventsService messageEventsService;

    @Override
    @Transactional
    public void execute(UpdateMessageDto dto) {
        Message message = messageRepository.findByIdAndChatId(dto.messageId(), dto.chatId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MESSAGE_NOT_FOUND, dto.messageId()));
        checkMessagesForEditingByCurrentUser(List.of(message));
        message.setContent(dto.newContent());
        messageEventsService.publishEvent(messageMapper.toMessageEditedEvent(message, dto.newContent()));
    }

    // =================================================================================================================
    // = Implementation
    // =================================================================================================================

    private void checkMessagesForEditingByCurrentUser(List<Message> messages) {
        messages.stream()
                .filter(message -> !authenticationService.isMessageBelongsToCurrentUser(message))
                .findFirst()
                .ifPresent(message -> {
                    throw new BusinessException(ErrorCode.EDIT_MESSAGE_BY_CURRENT_USER_FORBIDDEN, message.getId());
                });
    }
}
