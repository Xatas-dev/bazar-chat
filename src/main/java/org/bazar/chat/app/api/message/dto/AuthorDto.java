package org.bazar.chat.app.api.message.dto;

import java.util.UUID;

/**
 * DTO автора сообщения
 *
 * @param userId Идентификатор автора
 * @param firstName Имя автора
 * @param lastName Фамилия автора
 * @param status Статус автора
 */
public record AuthorDto(
        UUID userId,
        String firstName,
        String lastName,
        AuthorStatus status
) {
}
