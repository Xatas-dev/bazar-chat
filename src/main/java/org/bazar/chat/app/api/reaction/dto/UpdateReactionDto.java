package org.bazar.chat.app.api.reaction.dto;

/**
 * DTO обновленной реакции
 *
 * @param messageId Идентификатор сообщения
 * @param reactionId Идентификатор реакции
 * @param count Количество опеределенных реакций на сообщении
 */
public record UpdateReactionDto(
        String messageId,
        String reactionId,
        Long count
) {
}
