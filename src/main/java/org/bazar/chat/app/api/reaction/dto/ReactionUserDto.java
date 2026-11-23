package org.bazar.chat.app.api.reaction.dto;

import java.util.UUID;

/**
 * DTO пользователя оставившего реакцию
 *
 * @param userId Идентификатор пользователя
 * @param displayName Имя пользователя
 * @param avatarUrl URL аватарки пользователя
 * @param status Статус пользователя
 */
public record ReactionUserDto(
        UUID userId,
        String displayName,
        String avatarUrl,
        UserStatus status
) {
}
