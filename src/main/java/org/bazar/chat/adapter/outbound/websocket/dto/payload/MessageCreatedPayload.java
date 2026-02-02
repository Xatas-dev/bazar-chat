package org.bazar.chat.adapter.outbound.websocket.dto.payload;

/**
 * Полезная нагрузка для события создания сообщений
 *
 * @param id Идентификатор сообщения
 * @param userId Идентификатор пользователя
 * @param content Текст сообщения
 * @param createdAt Дата и время создания сообщения
 */
public record MessageCreatedPayload(
        Long id,
        String userId,
        String content,
        String createdAt
) {
}
