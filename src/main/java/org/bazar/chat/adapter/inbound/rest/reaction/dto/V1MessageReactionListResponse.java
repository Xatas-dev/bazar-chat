package org.bazar.chat.adapter.inbound.rest.reaction.dto;

import java.util.List;
import java.util.UUID;

public record V1MessageReactionListResponse(
        List<V1MessageReactionEntry> reactions
) {
    public record V1MessageReactionEntry(
            Long reactionId,
            List<V1ReactionUserEntry> users
    ) {
    }

    public record V1ReactionUserEntry(
            UUID userId,
            String firstName,
            String lastName,
            String status
    ) {
    }
}
