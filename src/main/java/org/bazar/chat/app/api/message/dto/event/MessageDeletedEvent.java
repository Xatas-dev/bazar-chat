package org.bazar.chat.app.api.message.dto.event;

import java.util.List;

public record MessageDeletedEvent(
        Long chatId,
        List<Long> ids
) implements ChatEvent {
    @Override
    public ChatEventType getType() {
        return ChatEventType.DELETED;
    }
}
