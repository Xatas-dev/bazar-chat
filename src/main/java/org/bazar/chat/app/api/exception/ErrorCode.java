package org.bazar.chat.app.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Код бизнес-ошибки
 */
public enum ErrorCode {
    /**
     * Не найден чат по его идентификатору
     */
    CHAT_BY_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "No chat with %s id"),
    /**
     * Не найден чат по идентификатору пространства
     */
    CHAT_BY_SPACE_NOT_FOUND(HttpStatus.NOT_FOUND, "No chat with %s spaceId"),
    /**
     * Удаление сообщения текущим пользователем невозможно
     */
    DELETE_MESSAGE_BY_CURRENT_USER_FORBIDDEN(HttpStatus.FORBIDDEN, "Current user can't delete message with id: %s"),
    /**
     * Редактирование сообщения текущим пользователем невозможно
     */
    EDIT_MESSAGE_BY_CURRENT_USER_FORBIDDEN(HttpStatus.FORBIDDEN, "Current user can't edit message with id: %s"),
    /**
     * Не найдено сообщение по его идентификатору
     */
    MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "No message with %s id"),
    /**
     * Максимальное количество реакций от одного пользователя на сообщение достигнуто
     */
    MAX_REACTIONS_PER_USER_ON_MESSAGE(HttpStatus.BAD_REQUEST, "Maximum number of reactions for user %s on message %s has been reached");

    @Getter
    private final HttpStatus status;
    private final String messageTemplate;

    ErrorCode(HttpStatus status, String messageTemplate) {
        this.status = status;
        this.messageTemplate = messageTemplate;
    }

    public String formatMessage(Object... args) {
        return String.format(messageTemplate, args);
    }
}
