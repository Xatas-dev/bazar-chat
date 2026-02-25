package org.bazar.chat.app.api.chat;

import org.bazar.chat.domain.chat.Chat;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью чата
 */
public interface ChatRepository {
    /**
     * Сохранить чат
     *
     * @param chat Чат для сохранения
     * @return Сохраненный чат
     */
    Chat save(Chat chat);

    /**
     * Получить чат по его идентификатору
     *
     * @param chatId Идентификатор чата
     * @return Найденный чат
     */
    Optional<Chat> findByChatId(Long chatId);

    /**
     * Получить чат по идентификатору пространства
     *
     * @param spaceId Идентификатор пространства
     * @return Найденный чат
     */
    Optional<Chat> findBySpaceId(Long spaceId);

    /**
     * Удалить чат
     *
     * @param chat Чат для удаления
     */
    void delete(Chat chat);
}
