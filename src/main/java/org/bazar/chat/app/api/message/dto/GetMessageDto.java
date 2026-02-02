package org.bazar.chat.app.api.message.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO получения информации по сообщению
 *
 * @param id Идентификатор сообщения
 * @param chatId Идентификатор чата
 * @param userId Идентификатор пользователя
 * @param content Текст сообщения
 * @param createdAt Дата и время создания сообщения
 * @param isDeletable Признак возможности удаления сообщения
 */
public record GetMessageDto(
        Long id,
        Long chatId,
        UUID userId,
        String content,
        Instant createdAt,
        Boolean isDeletable
) {
}
