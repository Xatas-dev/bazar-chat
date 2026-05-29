package org.bazar.chat.app.api.message.dto.event;

/**
 * Тип события
 */
public enum ChatEventType {
    /**
     * Создание сообщения
     */
    CREATED,
    /**
     * Удаление сообщения
     */
    DELETED,
    /**
     * Редактирование сообщения
     */
    EDITED,
    /**
     * Изменение реакции на сообщение
     */
    REACTION_CHANGED
}
