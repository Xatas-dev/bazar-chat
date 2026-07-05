package org.bazar.chat.adapter.outbound.websocket;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.message.MessageEventsService;
import org.bazar.chat.app.api.message.dto.event.ChatEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Реализация интерфейса публикации и получения событий
 */
@Component
@RequiredArgsConstructor
public class MessageWebSocketServiceImpl implements MessageEventsService {
    private final ApplicationEventPublisher publisher;

    @Override
    public void publishEvent(ChatEvent event) {
        publisher.publishEvent(event);
    }
}
