package org.bazar.chat.app.api.auth;

import org.bazar.chat.domain.message.Message;

import java.util.UUID;

/**
 * Сервис для работы с аунтификацией пользователя
 */
public interface AuthenticationService {
    /**
     * Получить идентификатор аутентифицированного пользователя
     *
     * @return идентификатор пользователя
     */
    UUID getAuthenticatedUserId();
    /**
     * Проверить, принадлежит ли сообщение аутентифицированному пользователю
     *
     * @param message сообщение
     * @return true, если сообщение принадлежит аутентифицированному пользователю, иначе false
     */
    boolean isMessageBelongsToCurrentUser(Message message);

    /**
     * Получить текущий токен JWT
     *
     * @return Токен JWT
     */
    String getCurrentJwtToken();
}
