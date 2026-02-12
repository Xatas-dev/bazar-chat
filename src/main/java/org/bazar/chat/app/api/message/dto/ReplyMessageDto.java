package org.bazar.chat.app.api.message.dto;

/**
 * DTO получения информации по сообщению, на которое было отправлено ответное сообщение
 *
 * @param id Идентификатор сообщения, на которое было отправлено ответное сообщение
 * @param author Автор сообщения, на которое было отправлено ответное сообщение
 * @param contentPreview Предварительный текст сообщения, на которое было отправлено ответное сообщение
 */
public record ReplyMessageDto(
        Long id,
        AuthorDto author,
        String contentPreview
) {
}
