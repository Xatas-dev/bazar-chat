package org.bazar.chat.adapter.outbound.websocket;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.message.dto.event.MessageCreatedEvent;
import org.bazar.chat.app.api.message.dto.event.MessageDeletedEvent;
import org.bazar.chat.app.api.message.dto.event.MessageEditedEvent;
import org.bazar.chat.app.api.message.dto.event.ReactionChangedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Слушатель по отправке событий в топик WS
 */
@Component
@RequiredArgsConstructor
@Async("eventExecutor")
public class MessageWebSocketListener {
    private final MessageWebSocketSender messageWebSocketSender;
    private final WebSocketMessageMapper mapper;

    @EventListener
    public void handle(MessageCreatedEvent event) {
        messageWebSocketSender.send(mapper.map(event));
    }

    @EventListener
    public void handle(MessageDeletedEvent event) {
        messageWebSocketSender.send(mapper.map(event));
    }

    @EventListener
    public void handle(MessageEditedEvent event) {
        messageWebSocketSender.send(mapper.map(event));
    }

    @EventListener
    public void handle(ReactionChangedEvent event) {
        messageWebSocketSender.send(mapper.map(event));
    }
}
