package org.bazar.chat.app.impl.auth;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.auth.AuthenticationService;
import org.bazar.chat.app.api.exception.BusinessException;
import org.bazar.chat.app.api.exception.ErrorCode;
import org.bazar.chat.domain.message.Message;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

/**
 * Реализация сервиса для работы с авторизацией пользователя
 */
@Component
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public UUID getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof JwtAuthenticationToken token)) {
            throw new IllegalStateException("No JWT authentication found");
        }
        return UUID.fromString(token.getToken().getSubject());
    }

    @Override
    public boolean isMessageBelongsToCurrentUser(Message message) {
        UUID currentUserId = getAuthenticatedUserId();
        return currentUserId.equals(message.getUserId());
    }

    @Override
    public String getCurrentJwtToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BusinessException(ErrorCode.NOT_AUTH);
        }
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return Objects.requireNonNull(jwt).getTokenValue();
    }
}
