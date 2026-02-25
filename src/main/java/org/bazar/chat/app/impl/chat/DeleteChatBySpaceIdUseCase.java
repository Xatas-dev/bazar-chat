package org.bazar.chat.app.impl.chat;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.chat.ChatRepository;
import org.bazar.chat.app.api.chat.DeleteChatBySpaceIdInbound;
import org.bazar.chat.app.api.exception.BusinessException;
import org.bazar.chat.app.api.exception.ErrorCode;
import org.bazar.chat.app.api.message.MessageRepository;
import org.bazar.chat.domain.chat.Chat;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация интерфейса для удаления чата по идентификатору пространства
 */
@Component
@RequiredArgsConstructor
public class DeleteChatBySpaceIdUseCase implements DeleteChatBySpaceIdInbound {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    @Override
    @Transactional
    public void execute(Long spaceId) {
        Chat chat = chatRepository.findBySpaceId(spaceId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CHAT_BY_SPACE_NOT_FOUND, spaceId));
        messageRepository.deleteAllByChat(chat);
        chatRepository.delete(chat);
    }
}
