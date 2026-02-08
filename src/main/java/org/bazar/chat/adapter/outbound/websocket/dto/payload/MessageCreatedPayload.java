package org.bazar.chat.adapter.outbound.websocket.dto.payload;

/**
 * Полезная нагрузка для события создания сообщений
 *
 * @param id Идентификатор сообщения
 * @param author Автор сообщения
 * @param content Текст сообщения
 * @param createdAt Дата и время создания сообщения
 */
public record MessageCreatedPayload(
        Long id,
        MessageAuthorPayload author,
        String content,
        String createdAt
) {
}
