package org.bazar.chat.app.api.auth;

import org.bazar.authorization.sdk.AuthorizationRequest;

/**
 * Сервис для работы с авторизацией пользователя
 */
public interface AuthorizationService {
    void authorize(AuthorizationRequest request);
}
