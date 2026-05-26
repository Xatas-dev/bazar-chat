package org.bazar.chat.app.api.message.dto.event;

import org.bazar.chat.app.api.message.dto.AuthorDto;

/**
 * Конкретная реализация события по изменению состояния реакции на сообщение
 *
 * @param chatId Идентификатор чата
 * @param messageId Идентификатор сообщения
 * @param reactionId Идентификатор реакции
 * @param count Текущее количество реакций данного типа
 * @param added true если реакция добавлена, false если удалена
 * @param author Информация об авторе действия
 */
public record ReactionChangedEvent(
        String chatId,
        String messageId,
        String reactionId,
        long count,
        boolean added,
        AuthorDto author) implements ChatEvent {

    @Override
    public ChatEventType getType() {
        return ChatEventType.REACTION_CHANGED;
    }
}

