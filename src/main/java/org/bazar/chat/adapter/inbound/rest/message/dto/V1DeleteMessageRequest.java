package org.bazar.chat.adapter.inbound.rest.message.dto;

import java.util.List;

public record V1DeleteMessageRequest(
        List<Long> messageIds
) {
}
