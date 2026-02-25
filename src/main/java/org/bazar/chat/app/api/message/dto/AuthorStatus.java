package org.bazar.chat.app.api.message.dto;

import org.bazar.chat.app.api.persona.model.UserDto;

public enum AuthorStatus {
    EXIST,
    UNKNOWN;

    public static AuthorStatus from(UserDto user) {
        return user != null ? EXIST : UNKNOWN;
    }
}
