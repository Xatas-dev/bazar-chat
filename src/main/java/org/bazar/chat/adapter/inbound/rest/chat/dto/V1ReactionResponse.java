package org.bazar.chat.adapter.inbound.rest.chat.dto;

public record V1ReactionResponse(
        String reactionId,
        String value,
        String type
) {
}
