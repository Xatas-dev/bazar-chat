package org.bazar.chat.app.api.message;

import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.domain.message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;

/**
 * Репозиторий для работы с сущностью сообщения
 */
public interface MessageRepository {
    /**
     * Удалить сообщения по чату
     *
     * @param chat Чат, по которому необходимо удалить сообщения
     */
    void deleteAllByChat(Chat chat);

    /**
     * Найти видимые сообщения по идентификатору чата
     *
     * @param chatId Идентификатор чата
     * @param pageable Информация по пагинации
     * @return Страница сообщений
     */
    Page<Message> findAllVisibleByChatId(Long chatId, Pageable pageable);

    /**
     * Сохранить сообщение
     *
     * @param message Сообщение для сохранения
     */
    void save(Message message);

    /**
     * Удалить все невидимые сообщения по их последней дате обновления
     *
     * @param updatedAt Дата обновления
     */
    void deleteInvisibleMessagesByUpdatedAt(Instant updatedAt);

    /**
     * Найти сообщения по идентификатору чата и идентификаторам сообщений
     *
     * @param chatId Идентификатор чата
     * @param messageIds Идентификаторы сообщений
     * @return Список найденных собщений
     */
    List<Message> findAllByChatIdAndMessageIds(Long chatId, List<Long> messageIds);
}
