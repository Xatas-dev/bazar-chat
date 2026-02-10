package org.bazar.chat.adapter.outbound.websocket.dto.payload;

/**
 * Полезная нагрузка для события ответа на сообщение
 *
 * @param id Идентификатор сообщения, на которое ответили
 * @param author Автор сообщения, на которое ответили
 * @param contentPreview Предварительный просмотр текста ответа
 */
public record MessageReplyPayload(
        Long id,
        MessageAuthorPayload author,
        String contentPreview
) {
}
