package org.bazar.chat.adapter.outbound.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bazar.chat.adapter.outbound.websocket.dto.WebSocketMessageCreatedEvent;
import org.bazar.chat.app.api.message.dto.MessageCreatedEvent;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageWebSocketSender {
    private static final String DESTINATION = "/topic/chat/%s";

    private final SimpMessagingTemplate webSocketTemplate;
    private final WebSocketMessageMapper mapper;

    public void sendMessageCreationEvent(MessageCreatedEvent event) {
        WebSocketMessageCreatedEvent webSocketEvent = mapper.mapToWebSocketMessageCreatedEvent(event);
        webSocketTemplate.convertAndSend(String.format(DESTINATION, webSocketEvent.chatId()), webSocketEvent);
    }
}
