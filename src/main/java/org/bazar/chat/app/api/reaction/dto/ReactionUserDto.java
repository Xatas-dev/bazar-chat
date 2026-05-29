package org.bazar.chat.app.api.reaction.dto;

import java.util.UUID;

/**
 * DTO пользователя оставившего реакцию
 *
 * @param userId Идентификатор пользователя
 * @param firstName Имя пользователя
 * @param lastName Фамилия пользователя
 * @param status Статус пользователя
 */
public record ReactionUserDto(
        UUID userId,
        String firstName,
        String lastName,
        UserStatus status
) {
}
