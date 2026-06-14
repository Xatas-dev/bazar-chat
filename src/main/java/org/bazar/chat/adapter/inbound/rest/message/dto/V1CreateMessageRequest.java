package org.bazar.chat.adapter.inbound.rest.message.dto;

public record V1CreateMessageRequest(
        String content,
        Long replyMessageId
) {
}
