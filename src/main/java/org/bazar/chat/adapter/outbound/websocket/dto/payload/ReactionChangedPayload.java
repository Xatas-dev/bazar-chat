package org.bazar.chat.adapter.outbound.websocket.dto.payload;

/**
 * Полезная нагрузка для события реакции на сообщение
 *
 * @param chatId Идентификатор чата
 * @param messageId Идентификатор сообщения
 * @param reactionId Идентификатор реакции
 * @param count Текущее количество реакций данного типа
 * @param added true если реакция добавлена, false если удалена
 * @param author Информация об авторе действия
 */
public record ReactionChangedPayload(
        String chatId,
        String messageId,
        String reactionId,
        long count,
        boolean added,
        MessageAuthorPayload author
) {
}
