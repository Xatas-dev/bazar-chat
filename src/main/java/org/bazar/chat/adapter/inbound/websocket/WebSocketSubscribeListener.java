package org.bazar.chat.adapter.inbound.websocket;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.pushsubscription.PresenceService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.Objects;
import java.util.UUID;

/**
 * Слушатель событий WS по регистрации пользователя в чате
 */
@Component
@RequiredArgsConstructor
public class WebSocketSubscribeListener {
    private static final String DESTINATION_CHAT_PREFIX = "/topic/chat/";

    private final PresenceService presenceService;

    @EventListener
    public void handleSubscribe(SessionSubscribeEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        UUID userId = UUID.fromString(Objects.requireNonNull(accessor.getUser()).getName());
        String destination = Objects.requireNonNull(accessor.getDestination());
        presenceService.register(userId, extractChatId(destination), accessor.getSessionId());
    }

    // =================================================================================================================
    // = Implementation
    // =================================================================================================================

    private Long extractChatId(String destination) {
        return Long.parseLong(destination.substring(DESTINATION_CHAT_PREFIX.length()));
    }
}
