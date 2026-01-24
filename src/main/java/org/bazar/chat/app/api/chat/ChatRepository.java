package org.bazar.chat.app.api.chat;

import org.bazar.chat.domain.chat.Chat;

public interface ChatRepository {
    Chat save(Chat chat);

    Chat findByChatId(Long chatId);

    Chat findBySpaceId(Long spaceId);

    void delete(Chat chat);
}
