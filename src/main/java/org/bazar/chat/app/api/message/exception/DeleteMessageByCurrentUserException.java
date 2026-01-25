package org.bazar.chat.app.api.message.exception;

public class DeleteMessageByCurrentUserException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Current user can't delete message with id: %s";

    public DeleteMessageByCurrentUserException(Long id) {
        super(String.format(MESSAGE_TEMPLATE, id));
    }
}
