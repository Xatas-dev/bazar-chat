package org.bazar.chat.adapter.inbound.rest;

import org.bazar.chat.app.api.chat.exception.ChatByIdNotFoundException;
import org.bazar.chat.app.api.chat.exception.ChatBySpaceIdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({ChatByIdNotFoundException.class, ChatBySpaceIdNotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
