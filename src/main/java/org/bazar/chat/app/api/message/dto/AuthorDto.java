package org.bazar.chat.app.api.message.dto;

import java.util.UUID;

/**
 * DTO автора сообщения
 *
 * @param userId Идентификатор автора
 * @param displayName Отображаемое имя автора
 * @param avatarUrl URL аватарки автора
 * @param status Статус автора
 */
public record AuthorDto(
        UUID userId,
        String displayName,
        String avatarUrl,
        AuthorStatus status
) {
}
