package org.bazar.chat.app.api.message.dto;

import java.time.Instant;
import java.util.List;

/**
 * DTO получения информации по сообщению
 *
 * @param id Идентификатор сообщения
 * @param chatId Идентификатор чата
 * @param content Текст сообщения
 * @param createdAt Дата и время создания сообщения
 * @param allowedActions Список разрешенных действий для пользователя над сообщением
 * @param author Автор сообщения
 * @param reply Информация по сообщению, на которое было отправлено ответное сообщение
 */
public record GetMessageDto(
        Long id,
        Long chatId,
        String content,
        Instant createdAt,
        List<AllowedActions> allowedActions,
        AuthorDto author,
        ReplyMessageDto reply
) {
}
