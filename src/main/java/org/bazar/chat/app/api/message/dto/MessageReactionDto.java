package org.bazar.chat.app.api.message.dto;

/**
 * DTO реакции на сообщение
 *
 * @param reactionId Идентификатор реакции
 * @param count Количество реакций
 * @param reactedByMe Есть ли эта реакция у текущего пользователя
 */
public record MessageReactionDto(
        String reactionId,
        long count,
        boolean reactedByMe
) {
}
