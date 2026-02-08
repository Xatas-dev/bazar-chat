package org.bazar.chat.adapter.outbound.persistence.chat;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.chat.ChatRepository;
import org.bazar.chat.domain.chat.Chat;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Имплементация репозитория для работы с сущностью чата
 */
@Component
@RequiredArgsConstructor
public class ChatJpaRepositoryAdapter implements ChatRepository {
    private final ChatJpaRepository chatJpaRepository;

    @Override
    public Chat save(Chat chat) {
        return chatJpaRepository.save(chat);
    }

    @Override
    public Optional<Chat> findByChatId(Long chatId) {
        return chatJpaRepository.findById(chatId);
    }

    @Override
    public Optional<Chat> findBySpaceId(Long spaceId) {
        return chatJpaRepository.findBySpaceId(spaceId);
    }

    @Override
    public void delete(Chat chat) {
        chatJpaRepository.delete(chat);
    }
}
