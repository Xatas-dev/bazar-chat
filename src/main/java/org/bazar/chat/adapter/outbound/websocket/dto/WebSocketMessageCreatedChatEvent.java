package org.bazar.chat.adapter.outbound.websocket.dto;

import org.bazar.chat.adapter.outbound.websocket.dto.payload.MessageCreatedPayload;
import org.bazar.chat.app.api.message.dto.event.ChatEventType;

public record WebSocketMessageCreatedChatEvent(
        Long chatId,
        MessageCreatedPayload payload
) implements WebSocketChatEvent {
    @Override
    public ChatEventType type() {
        return ChatEventType.CREATED;
    }
}
