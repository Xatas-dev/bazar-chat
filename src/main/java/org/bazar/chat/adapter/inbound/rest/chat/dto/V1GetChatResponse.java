package org.bazar.chat.adapter.inbound.rest.chat.dto;

import java.time.Instant;

public record V1GetChatResponse(
        Long id,
        Long spaceId,
        Instant createdAt
) {
}
