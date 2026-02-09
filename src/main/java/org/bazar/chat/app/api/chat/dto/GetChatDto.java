package org.bazar.chat.app.api.chat.dto;

import java.time.Instant;

/**
 * DTO получения информации по чату
 *
 * @param id Идентификатор чата
 * @param spaceId Идентификтор пространства
 * @param createdAt Дата и время создания чата
 */
public record GetChatDto(
        Long id, Long spaceId, Instant createdAt
) {
}
