package org.bazar.chat.app.api.message.dto;

import java.time.Instant;

/**
 * DTO получения информации по сообщению
 *
 * @param id Идентификатор сообщения
 * @param chatId Идентификатор чата
 * @param content Текст сообщения
 * @param createdAt Дата и время создания сообщения
 * @param isDeletable Признак возможности удаления сообщения
 * @param author Автор сообщения
 */
public record GetMessageDto(
        Long id,
        Long chatId,
        String content,
        Instant createdAt,
        Boolean isDeletable,
        AuthorDto author
) {
}
