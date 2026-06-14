package org.bazar.chat.adapter.inbound.rest.message.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record V1GetMessageResponse(
        Long id,
        Long chatId,
        V1AuthorMessageResponse author,
        String content,
        Instant createdAt,
        List<String> allowedActions,
        V1ReplyMessageResponse reply,
        List<V1ReactionSummaryResponse> reactions
) {
    public record V1AuthorMessageResponse(
            UUID userId,
            String firstName,
            String lastName,
            String status
    ) {}

    public record V1ReplyMessageResponse(
            Long id,
            V1AuthorMessageResponse author,
            String contentPreview
    ) {}

    public record V1ReactionSummaryResponse(
            String reactionId,
            Long count,
            Boolean reactedByMe
    ) {}
}
