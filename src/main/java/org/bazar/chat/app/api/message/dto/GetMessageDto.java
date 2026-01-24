package org.bazar.chat.app.api.message.dto;

import java.time.Instant;
import java.util.UUID;

public record GetMessageDto(
        Long id,
        Long chatId,
        UUID userId,
        String content,
        Instant createdAt
) {
}
