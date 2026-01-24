package org.bazar.chat.app.api.message;

import org.bazar.chat.app.api.message.dto.MessageCreatedEvent;

public interface MessageEventsService {
    void sendCreatedEvent(MessageCreatedEvent event);
}
