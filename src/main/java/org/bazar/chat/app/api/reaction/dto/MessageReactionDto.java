package org.bazar.chat.app.api.reaction.dto;

import java.util.List;

/**
 * DTO для реакции и пользователей оставивших ее
 *
 * @param reactionId Идентификатор реакции
 * @param users Пользователи оставившие реакцию
 */
public record MessageReactionDto(
        Long reactionId,
        List<ReactionUserDto> users
) {
}
