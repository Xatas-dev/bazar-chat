package org.bazar.chat.app.api.message;

import org.bazar.chat.app.api.message.dto.event.ChatEvent;

public interface MessageEventsService {
    void publishEvent(Long chatId, ChatEvent event);
}
