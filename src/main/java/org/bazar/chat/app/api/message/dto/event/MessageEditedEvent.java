package org.bazar.chat.app.api.message.dto.event;

/**
 * Конкретная реализация события по редактированию сообщений
 *
 * @param chatId Идентификатор чата
 * @param messageId Идентификатор сообщения
 * @param newContent Новое содержимое сообщения
 */
public record MessageEditedEvent(Long chatId, Long messageId, String newContent) implements ChatEvent {
    @Override
    public ChatEventType getType() {
        return ChatEventType.EDITED;
    }
}
