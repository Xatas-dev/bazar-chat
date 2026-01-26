package org.bazar.chat.adapter.outbound.persistence.message;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.message.MessageRepository;
import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.domain.message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageJpaRepositoryAdapter implements MessageRepository {
    private final MessageJpaRepository messageJpaRepository;

    @Override
    public void deleteAllByChat(Chat chat) {
        messageJpaRepository.deleteAllByChat(chat);
    }

    @Override
    public Page<Message> findAllVisibleByChatId(Long chatId, Pageable pageable) {
        return messageJpaRepository.findAllByChatIdAndVisibleTrueOrderByCreatedAtDesc(chatId, pageable);
    }

    @Override
    public void save(Message message) {
        messageJpaRepository.save(message);
    }
}
