package org.bazar.chat.adapter.outbound.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bazar.chat.adapter.outbound.websocket.dto.WebSocketChatEvent;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageWebSocketSender {
    private static final String DESTINATION = "/topic/chat/%s";

    private final SimpMessagingTemplate webSocketTemplate;

    public void send(Long chatId, WebSocketChatEvent event) {
        webSocketTemplate.convertAndSend(String.format(DESTINATION, chatId), event);
    }
}
