package org.bazar.chat.adapter.outbound.websocket.dto;

import org.bazar.chat.app.api.message.dto.event.ChatEventType;

/**
 * Абстракция для событий WebSocket
 */
public interface WebSocketChatEvent {
    /**
     * Получить тип события
     *
     * @return Тип события
     */
    ChatEventType type();

    /**
     * Получить идентификатор чата
     *
     * @return Идентификатор чата
     */
    Long chatId();

    /**
     * Получить полезную нагрузку события
     *
     * @return Полезная нагрузка события
     */
    Object payload();
}
