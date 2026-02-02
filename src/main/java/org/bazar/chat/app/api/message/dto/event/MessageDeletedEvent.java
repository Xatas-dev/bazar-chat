package org.bazar.chat.app.api.message.dto.event;

import java.util.List;

/**
 * Конкретная реализация события по удалению сообщений
 *
 * @param chatId Идентификатор чата
 * @param ids Массив идентификаторов сообщений
 */
public record MessageDeletedEvent(
        Long chatId,
        List<Long> ids
) implements ChatEvent {
    @Override
    public ChatEventType getType() {
        return ChatEventType.DELETED;
    }
}
