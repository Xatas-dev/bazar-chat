package org.bazar.chat.app.impl.message;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.exception.BusinessException;
import org.bazar.chat.app.api.exception.ErrorCode;
import org.bazar.chat.app.api.message.DeleteMessagesInbound;
import org.bazar.chat.app.api.message.MessageEventsService;
import org.bazar.chat.app.api.message.MessageRepository;
import org.bazar.chat.app.service.AuthorizationService;
import org.bazar.chat.domain.message.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Реализация интерфейса для удаления сообщений в чате
 */
@Component
@RequiredArgsConstructor
public class DeleteMessagesUseCase implements DeleteMessagesInbound {
    private final MessageRepository messageRepository;
    private final AuthorizationService authorizationService;
    private final MessageEventsService messageEventsService;
    private final MessageMapper messageMapper;

    @Override
    @Transactional
    public void execute(Long chatId, List<Long> messageIds) {
        List<Message> messagesToDelete = messageRepository.findAllByChatIdAndMessageIds(chatId, messageIds);
        checkMessagesForDeletingByCurrentUser(messagesToDelete);
        messagesToDelete.forEach(message -> message.setVisible(false));
        messageEventsService.publishEvent(messageMapper.toMessageDeletedEvent(chatId, messageIds));
    }

    // =================================================================================================================
    // = Implementation
    // =================================================================================================================

    private void checkMessagesForDeletingByCurrentUser(List<Message> messages) {
        messages.stream()
                .filter(message -> !authorizationService.isMessageBelongsToCurrentUser(message))
                .findFirst()
                .ifPresent(message -> {
                    throw new BusinessException(ErrorCode.DELETE_MESSAGE_BY_CURRENT_USER_FORBIDDEN, message.getId());
                });
    }
}
