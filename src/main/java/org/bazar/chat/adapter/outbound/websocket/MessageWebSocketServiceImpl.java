package org.bazar.chat.adapter.outbound.websocket;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.message.MessageEventsService;
import org.bazar.chat.app.api.message.dto.event.ChatEvent;
import org.bazar.chat.app.api.message.dto.event.MessageCreatedEvent;
import org.bazar.chat.app.api.message.dto.event.MessageDeletedEvent;
import org.springframework.stereotype.Component;

/**
 * Имплементация (клиент) интерфейса публикации и получения событий для WebSocket
 */
@Component
@RequiredArgsConstructor
public class MessageWebSocketServiceImpl implements MessageEventsService {
    private final MessageWebSocketSender messageWebSocketSender;
    private final WebSocketMessageMapper mapper;

    @Override
    public void publishEvent(ChatEvent event) {
        // думаю, что добавлю сюда Стратегию, но через switch как будто читабелньей так что хз
        switch (event.getType()) {
            case CREATED -> messageWebSocketSender.send(mapper.map((MessageCreatedEvent) event));
            case DELETED -> messageWebSocketSender.send(mapper.map((MessageDeletedEvent) event));
        }
    }
}
