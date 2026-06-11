package org.bazar.chat.adapter.outbound.webpush;

import org.bazar.chat.app.api.message.dto.AuthorDto;

/**
 * DTO для отправки push-уведомлений о сообщении
 *
 * @param author Автор сообшения
 * @param content Содержимое сообщения
 * @param spaceId Идентификатор пространства
 */
public record PushPayloadDto(
        AuthorDto author,
        String content,
        Long spaceId
) {
}
