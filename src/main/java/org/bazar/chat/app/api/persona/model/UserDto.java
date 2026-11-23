package org.bazar.chat.app.api.persona.model;


import java.util.UUID;

/**
 * DTO для интеграции с bazar-space
 *
 * @param userId Идентификатор автора
 * @param displayName Отображаемое имя пользователя
 */
public record UserDto(
        UUID userId,
        String displayName,
        String avatarUrl
) {
}
