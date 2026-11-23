package org.bazar.chat.adapter.outbound.websocket.dto.payload;

import org.bazar.chat.app.api.message.dto.AuthorStatus;

import java.util.UUID;

/**
 * Автор сообщения для полезной нагрузки для события создания сообщения
 *
 * @param userId Идентификатор автора
 * @param displayName Отображаемое имя автора
 * @param avatarUrl URL аватарки автора
 * @param status Статус автора
 */
public record MessageAuthorPayload(
        UUID userId,
        String displayName,
        String avatarUrl,
        AuthorStatus status
) {
}
