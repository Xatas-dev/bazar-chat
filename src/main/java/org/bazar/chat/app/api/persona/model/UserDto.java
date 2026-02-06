package org.bazar.chat.app.api.persona.model;


import java.util.UUID;

/**
 * DTO для интеграции с bazar-space
 *
 * @param userId Идентификатор автора
 * @param firstName Имя автора
 * @param lastName Фамилия автора
 */
public record UserDto(
        UUID userId,
        String firstName,
        String lastName
) {
}
