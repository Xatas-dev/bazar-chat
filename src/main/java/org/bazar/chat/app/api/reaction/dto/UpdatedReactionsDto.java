package org.bazar.chat.app.api.reaction.dto;

import java.util.List;

/**
 * DTO обновленных реакций
 *
 * @param messageId Идентификатор сообщения
 * @param updatedReactions Обновленные реакции
 */
public record UpdatedReactionsDto(
        Long messageId,
        List<UpdatedReactionDto> updatedReactions
) {
}
