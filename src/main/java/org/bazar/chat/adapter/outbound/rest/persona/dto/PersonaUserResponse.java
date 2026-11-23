package org.bazar.chat.adapter.outbound.rest.persona.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Ответ сервиса bazar-persona на получение информации по пользователям
 *
 * @param id Идентификатор пользователя
 * @param username Никнейм пользователя
 * @param displayName Отображаемое имя
 * @param avatarUrl URL аватарки
 * @param createdAt Дата создании записи в сервисе bazar-persona
 * @param updatedAt Дата обновления записи в сервисе bazar-persona
 */
public record PersonaUserResponse(
        UUID id,
        String username,
        String displayName,
        String avatarUrl,
        Instant createdAt,
        Instant updatedAt
) {
}
