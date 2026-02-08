package org.bazar.chat.adapter.outbound.rest.persona.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Ответ сервиса bazar-persona на получение информации по пользователям
 *
 * @param id Идентификатор пользователя
 * @param createdAt Дата создании записи в сервисе bazar-persona
 * @param updatedAt Дата обновления записи в сервисе bazar-persona
 * @param userName Никнейм пользователя
 * @param email Электронная почта пользователя
 * @param firstName Имя пользователя
 * @param lastName Фамилия пользователя
 * @param userPic URL к фотографии пользователя
 */
public record PersonaUserResponse(
        UUID id,
        Instant createdAt,
        Instant updatedAt,
        String userName,
        String email,
        String firstName,
        String lastName,
        String userPic
) {
}
