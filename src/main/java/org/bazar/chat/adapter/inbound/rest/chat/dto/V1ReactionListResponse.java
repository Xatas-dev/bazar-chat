package org.bazar.chat.adapter.inbound.rest.chat.dto;

import java.util.List;

public record V1ReactionListResponse(
        List<V1ReactionResponse> reactions
) {
}
