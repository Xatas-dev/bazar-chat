package org.bazar.chat.adapter.outbound.websocket.dto.payload;

import org.bazar.chat.app.api.message.dto.AuthorStatus;

import java.util.UUID;

/**
 * Автор сообщения для полезной нагрузки для события создания сообщения
 *
 * @param userId Идентификатор автора
 * @param firstName Имя автора
 * @param lastName Фамилия автора
 * @param status Статус автора
 */
public record MessageAuthorPayload(
        UUID userId,
        String firstName,
        String lastName,
        AuthorStatus status
) {
}
