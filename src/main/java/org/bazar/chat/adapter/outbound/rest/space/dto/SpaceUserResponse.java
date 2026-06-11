package org.bazar.chat.adapter.outbound.rest.space.dto;

import java.util.Set;
import java.util.UUID;

/**
 * Ответ bazar-space на запрос по получению пользователей пространства
 *
 * @param users Пользователи пространства
 */
public record SpaceUserResponse(
        Set<UserInSpaceDto> users
) {
    public record UserInSpaceDto(
            UUID userId
    ) {}
}
