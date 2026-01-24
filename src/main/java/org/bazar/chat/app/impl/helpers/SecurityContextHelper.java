package org.bazar.chat.app.impl.helpers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SecurityContextHelper {
    public UUID getAuthenticatedUserId() {
        JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        assert token != null;

        return UUID.fromString(token.getToken().getSubject());
    }
}
