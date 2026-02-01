package org.bazar.chat.adapter.outbound.websocket.dto.payload;

public record MessageCreatedPayload(
        Long id,
        String userId,
        String content,
        String createdAt
) {
}
