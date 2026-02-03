package org.bazar.chat.adapter.outbound.websocket.dto.payload;

import java.util.List;

/**
 * Полезная нагрузка для события удаления сообщений
 *
 * @param ids Список идентификаторов сообщений для удаления
 */
public record MessageDeletedPayload(
        List<Long> ids
) {
}
