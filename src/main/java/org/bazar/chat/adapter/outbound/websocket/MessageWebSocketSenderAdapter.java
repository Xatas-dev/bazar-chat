package org.bazar.chat.adapter.outbound.websocket;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.message.MessageEventsService;
import org.bazar.chat.app.api.message.dto.event.ChatEvent;
import org.bazar.chat.app.api.message.dto.event.MessageCreatedEvent;
import org.bazar.chat.app.api.message.dto.event.MessageDeletedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageWebSocketSenderAdapter implements MessageEventsService {
    private final MessageWebSocketSender messageWebSocketSender;
    private final WebSocketMessageMapper mapper;

    @Override
    public void publishEvent(Long chatId, ChatEvent event) {
        // думаю, что добавлю сюда Стратегию, но через switch как будто читабелньей так что хз
        switch (event.getType()) {
            case CREATED -> messageWebSocketSender.send(chatId, mapper.map(chatId, (MessageCreatedEvent) event));
            case DELETED -> messageWebSocketSender.send(chatId, mapper.map(chatId, (MessageDeletedEvent) event));
        }
    }
}
