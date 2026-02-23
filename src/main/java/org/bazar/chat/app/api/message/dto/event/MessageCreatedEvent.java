package org.bazar.chat.app.api.message.dto.event;

import org.bazar.chat.app.api.message.dto.AllowedActions;
import org.bazar.chat.app.api.message.dto.AuthorDto;
import org.bazar.chat.app.api.message.dto.ReplyMessageDto;

import java.time.Instant;
import java.util.List;

/**
 * Конкретная реализация события по созданию сообщения
 *
 * @param id Идентификатор сообщения
 * @param chatId Идентификатор чата
 * @param content Текст сообщения
 * @param createdAt Дата и время создания сообщения
 * @param allowedActions Разрешения пользователя над сообщением
 */
public record MessageCreatedEvent(
        Long id,
        Long chatId,
        String content,
        Instant createdAt,
        AuthorDto author,
        ReplyMessageDto reply,
        List<AllowedActions> allowedActions) implements ChatEvent {
    @Override
    public ChatEventType getType() {
        return ChatEventType.CREATED;
    }
}
