package org.bazar.chat.adapter.outbound.websocket;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.message.MessageEventsService;
import org.bazar.chat.app.api.message.dto.MessageCreatedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageWebSocketSenderAdapter implements MessageEventsService {
    private final MessageWebSocketSender messageWebSocketSender;

    @Override
    public void sendCreatedEvent(MessageCreatedEvent event) {
        messageWebSocketSender.sendMessageCreationEvent(event);
    }
}
