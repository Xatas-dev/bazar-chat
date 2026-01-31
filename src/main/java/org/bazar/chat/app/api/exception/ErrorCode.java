package org.bazar.chat.app.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ErrorCode {
    CHAT_BY_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "No chat with %s id"),
    CHAT_BY_SPACE_NOT_FOUND(HttpStatus.NOT_FOUND, "No chat with %s spaceId"),
    DELETE_MESSAGE_BY_CURRENT_USER_FORBIDDEN(HttpStatus.FORBIDDEN, "Current user can't delete message with id: %s");

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
