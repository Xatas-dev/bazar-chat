package org.bazar.chat.adapter.inbound.websocket;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.pushsubscription.PresenceService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * Слушатель событий WS по отписке пользователя от чата
 */
@Component
@RequiredArgsConstructor
public class WebSocketDisconnectListener {
    private final PresenceService presenceService;

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        presenceService.unregister(accessor.getSessionId());
    }
}
