package org.bazar.chat.adapter.inbound.kafka.dto;

/**
 * Сообщение из топика удаления пространства
 *
 * @param spaceId Идентификатор пространства
 */
public record SpaceDeletingDto(Long spaceId) {
}
