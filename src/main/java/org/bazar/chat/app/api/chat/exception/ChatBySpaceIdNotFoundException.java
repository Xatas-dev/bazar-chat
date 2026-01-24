package org.bazar.chat.app.api.chat.exception;

public class ChatBySpaceIdNotFoundException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "No chat with %s spaceId";

    public ChatBySpaceIdNotFoundException(Long id) {
        super(String.format(MESSAGE_TEMPLATE, id));
    }
}
