package org.bazar.chat.app.api.message.dto.event;

/**
 * Абстракция для событий
 */
public interface ChatEvent {
    /**
     * Получить тип события
     *
     * @return Тип события
     */
    ChatEventType getType();
}
