package org.bazar.chat.adapter.inbound.rest.reaction.dto;

import java.util.List;

public record V1ReactionUpdateResponse(
        String messageId,
        List<V1UpdatedReaction> reactions
) {

    public record V1UpdatedReaction(
            String reactionId,
            Long count
    ) {
    }
}
