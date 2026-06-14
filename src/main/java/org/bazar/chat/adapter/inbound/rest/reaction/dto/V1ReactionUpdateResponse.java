package org.bazar.chat.adapter.inbound.rest.reaction.dto;

public record V1ReactionUpdateResponse(
        String messageId,
        String reactionId,
        Long count
) {
}
