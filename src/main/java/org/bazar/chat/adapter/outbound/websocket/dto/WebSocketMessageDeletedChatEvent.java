package org.bazar.chat.adapter.outbound.websocket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bazar.chat.adapter.outbound.websocket.dto.payload.MessageDeletedPayload;
import org.bazar.chat.app.api.message.dto.event.ChatEventType;

/**
 * Реализация события удаления сообщений в WebSocket
 *
 * @param chatId Идентификатор чата
 * @param payload Полезная нагрузка события
 */
public record WebSocketMessageDeletedChatEvent(
        Long chatId,
        MessageDeletedPayload payload
) implements WebSocketChatEvent {
    @Override
    public ChatEventType type() {
        return ChatEventType.DELETED;
    }

    @JsonProperty
    public ChatEventType getType() {
        return type();
    }
}
