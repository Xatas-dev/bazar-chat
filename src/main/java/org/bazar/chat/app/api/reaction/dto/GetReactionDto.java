package org.bazar.chat.app.api.reaction.dto;

public record GetReactionDto(
        Long reactionId,
        String value,
        String type
) {
}
