package org.bazar.chat.app.api.message.dto;

public record CreateMessageDto(
        String content,
        Long chatId
) {
}
