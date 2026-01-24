package org.bazar.chat.app.api.chat.dto;

import java.time.Instant;

public record GetChatDto(
        Long id, Long spaceId, Instant createdAt
) {
}
