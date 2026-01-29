package org.bazar.chat.adapter.outbound.websocket.dto;

import org.bazar.chat.adapter.outbound.websocket.dto.payload.MessageDeletedPayload;
import org.bazar.chat.app.api.message.dto.event.ChatEventType;

public record WebSocketMessageDeletedChatEvent(
        Long chatId,
        MessageDeletedPayload payload
) implements WebSocketChatEvent {
    @Override
    public ChatEventType type() {
        return ChatEventType.DELETED;
    }
}
