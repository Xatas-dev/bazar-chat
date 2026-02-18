package org.bazar.chat.adapter.outbound.websocket.dto.payload;
/**
 * Полезная нагрузка для события редактирования сообщений
 *
 * @param messageId Идентификатор сообщения, которое было отредактировано
 * @param newContent Новое содержимое сообщения
 */
public record MessageEditedPayload(
        Long messageId,
        String newContent
) {
}
