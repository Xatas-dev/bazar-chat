package org.bazar.chat.app.api.chat;

import org.bazar.chat.domain.chat.Chat;

/**
 * Репозиторий для работы с сущностью чата
 */
public interface ChatRepository {
    /**
     * Сохранить чат
     *
     * @param chat Чат для сохранения
     * @return Сохраенный чат
     */
    Chat save(Chat chat);

    /**
     * Получить чат по его идентификатору
     *
     * @param chatId Идентификатор чата
     * @return Найденный чат
     */
    Chat findByChatId(Long chatId);

    /**
     * Получить чат по идентификатору пространства
     *
     * @param spaceId Идентификатор пространства
     * @return Найденный чат
     */
    Chat findBySpaceId(Long spaceId);

    /**
     * Удалить чат
     *
     * @param chat Чат для удаления
     */
    void delete(Chat chat);
}
