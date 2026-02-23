package org.bazar.chat.app.api.message.dto;

/**
 * Действия с сообщением, доступные для текущего пользователя
 */
public enum AllowedActions {
    /**
     * Удаление сообщения
     */
    DELETE,
    /**
     * Редактирование сообщения
     */
    EDIT
}
