package org.bazar.chat.adapter.outbound.persistence.chat;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.chat.ChatRepository;
import org.bazar.chat.app.api.chat.exception.ChatByIdNotFoundException;
import org.bazar.chat.app.api.chat.exception.ChatBySpaceIdNotFoundException;
import org.bazar.chat.domain.chat.Chat;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatJpaRepositoryAdapter implements ChatRepository {
    private final ChatJpaRepository chatJpaRepository;

    @Override
    public Chat save(Chat chat) {
        return chatJpaRepository.save(chat);
    }

    @Override
    public Chat findByChatId(Long chatId) {
        return chatJpaRepository.findById(chatId)
                .orElseThrow(() -> new ChatByIdNotFoundException(chatId));
    }

    @Override
    public Chat findBySpaceId(Long spaceId) {
        return chatJpaRepository.findBySpaceId(spaceId)
                .orElseThrow(() -> new ChatBySpaceIdNotFoundException(spaceId));
    }

    @Override
    public void delete(Chat chat) {
        chatJpaRepository.delete(chat);
    }
}
