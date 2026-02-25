package org.bazar.chat.adapter.outbound.persistence.message;

import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.domain.message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

/**
 * Jpa репозиторий для работы с сущностью сообщения
 */
public interface MessageJpaRepository extends JpaRepository<Message, Long> {
    /**
     * Удалить все сообщения по чату
     *
     * @param chat Чат
     */
    void deleteAllByChat(Chat chat);

    /**
     * Найти видимые сообщения по идентификатору чата, отсортированные по дате создания
     *
     * @param chatId Идентификатор чата
     * @param pageable Информация для пагинации
     * @return Страница сообщений
     */
    @EntityGraph(attributePaths = "replyMessage")
    Page<Message> findAllByChatIdAndVisibleTrueOrderByCreatedAtDesc(Long chatId, Pageable pageable);

    /**
     * Найти сообщения по идентификатору чата, идентификатору сообщения и признаку видимости
     *
     * @param chatId Идентификатор чата
     * @param messageIds Список идентификаторов сообщений
     * @param visible Признак видимости
     * @return Список найденных сообщений
     */
    List<Message> findAllByChatIdAndIdInAndVisible(Long chatId, List<Long> messageIds, boolean visible);

    /**
     * Удалить невидимые сообщения по последней дате обновления
     *
     * @param updatedAt Дата обновления
     */
    void deleteAllByVisibleFalseAndUpdatedAtLessThan(Instant updatedAt);
}
