package org.bazar.chat.adapter.outbound.websocket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bazar.chat.adapter.outbound.websocket.dto.payload.MessageEditedPayload;
import org.bazar.chat.app.api.message.dto.event.ChatEventType;

/**
 * Реализация события редактирования сообщений в WebSocket
 *
 * @param chatId Идентификатор чата
 * @param payload Полезная нагрузка события
 */
public record WebSocketMessageEditedChatEvent(
        Long chatId,
        MessageEditedPayload payload
) implements WebSocketChatEvent {
    @Override
    public ChatEventType type() {
        return ChatEventType.EDITED;
    }

    @JsonProperty
    public ChatEventType getType() {
        return type();
    }
}
