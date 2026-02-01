package org.bazar.chat.app.api.message;

import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.domain.message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;

public interface MessageRepository {
    void deleteAllByChat(Chat chat);

    Page<Message> findAllVisibleByChatId(Long chatId, Pageable pageable);

    void save(Message message);

    void deleteInvisibleMessagesByUpdatedAt(Instant updatedAt);

    List<Message> findAllByChatIdAndMessageIds(Long chatId, List<Long> messageIds);
}
