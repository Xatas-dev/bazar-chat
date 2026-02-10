package org.bazar.chat.app.api.message.dto;

/**
 * DTO получения информации по созданному сообщению
 *
 * @param content Текст сообщения
 * @param chatId Идентификатор чата
 */
public record CreateMessageDto(
        String content,
        Long chatId,
        Long replyMessageId
) {
}
