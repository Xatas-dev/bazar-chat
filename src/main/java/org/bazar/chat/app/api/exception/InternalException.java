package org.bazar.chat.app.api.exception;

public class InternalException extends RuntimeException {
    public InternalException(String message) {
        super(message);
    }

    public InternalException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
