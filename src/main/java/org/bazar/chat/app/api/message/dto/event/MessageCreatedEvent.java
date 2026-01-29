package org.bazar.chat.app.api.message.dto.event;

import java.time.Instant;
import java.util.UUID;

public record MessageCreatedEvent(
        Long id,
        UUID userId,
        String content,
        Instant createdAt) implements ChatEvent {
    @Override
    public ChatEventType getType() {
        return ChatEventType.CREATED;
    }
}
