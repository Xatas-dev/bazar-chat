package org.bazar.chat.app.api.reaction.dto;

/**
 * DTO обновленной реакции
 *
 * @param reactionId Идентификатор реакции
 * @param count Количество опеределенных реакций на сообщении
 */
public record UpdatedReactionDto(
        Long reactionId,
        Long count
) {
}
