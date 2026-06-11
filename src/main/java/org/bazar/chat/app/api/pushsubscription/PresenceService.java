package org.bazar.chat.app.api.pushsubscription;

import java.util.UUID;

/**
 * Сервис управления присутствием пользователя в чате
 */
public interface PresenceService {
    /**
     * Зарегистрировать пользователя и сессию в чате
     *
     * @param userId Идентификатор пользователя
     * @param chatId Идентификатор чата
     * @param sessionId Идентификатор сессии
     */
    void register(UUID userId, Long chatId, String sessionId);

    /**
     * Удалить зарегистрированную сессию
     *
     * @param sessionId Идентификатор сессии
     */
    void unregister(String sessionId);

    /**
     * Смотрит ли пользователь определенный чат
     *
     * @param userId Идентификатор пользователя
     * @param chatId Идентификатор чата
     * @return Результат проверки
     */
    boolean isViewingChat(UUID userId, Long chatId);
}
