package org.bazar.chat.app.api.message;

/**
 * DTO для обновления содержимого сообщения
 *
 * @param chatId Идентификатор чата
 * @param messageId Идентификатор сообщения
 * @param newContent Новое содержимое сообщения
 */
public record UpdateMessageDto(
        Long chatId,
        Long messageId,
        String newContent
) {
}
