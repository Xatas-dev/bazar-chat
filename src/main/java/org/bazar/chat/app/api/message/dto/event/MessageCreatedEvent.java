package org.bazar.chat.app.api.message.dto.event;

import java.time.Instant;
import java.util.UUID;

/**
 * Конкретная реализация события по созданию сообщения
 *
 * @param id Идентификатор сообщения
 * @param userId Идентификатор пользователя
 * @param chatId Идентификатор чата
 * @param content Текст сообщения
 * @param createdAt Дата и время создания сообщения
 */
public record MessageCreatedEvent(
        Long id,
        UUID userId,
        Long chatId,
        String content,
        Instant createdAt) implements ChatEvent {
    @Override
    public ChatEventType getType() {
        return ChatEventType.CREATED;
    }
}
