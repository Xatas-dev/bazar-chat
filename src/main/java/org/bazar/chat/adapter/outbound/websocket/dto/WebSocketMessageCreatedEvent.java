package org.bazar.chat.adapter.outbound.websocket.dto;

public record WebSocketMessageCreatedEvent(
        Long id,
        Long chatId,
        String userId,
        String content,
        String createdAt
) {
}
