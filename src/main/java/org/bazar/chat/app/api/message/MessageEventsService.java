package org.bazar.chat.app.api.message;

import org.bazar.chat.app.api.message.dto.event.ChatEvent;

/**
 * Интерфейс публикации и получения событий
 */
public interface MessageEventsService {
    /**
     * Публикация события
     *
     * @param event Событие
     */
    void publishEvent(ChatEvent event);
}
