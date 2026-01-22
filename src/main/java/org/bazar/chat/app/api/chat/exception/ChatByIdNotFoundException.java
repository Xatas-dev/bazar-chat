package org.bazar.chat.app.api.chat.exception;

public class ChatByIdNotFoundException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "No chat with %s id";

    public ChatByIdNotFoundException(Long id) {
        super(String.format(MESSAGE_TEMPLATE, id));
    }
}
