package org.bazar.chat.app.api.space.dto;

import java.util.UUID;

/**
 * DTO пользователя в пространстве
 *
 * @param userId Идентификатор пользователя
 */
public record SpaceUserDto(
        UUID userId
) {
}
