package org.bazar.chat.app.api.reaction.dto;

import org.bazar.chat.app.api.persona.model.UserDto;

public enum UserStatus {
    EXIST,
    UNKNOWN;

    public static UserStatus from(UserDto user) {
        return user != null ? EXIST : UNKNOWN;
    }
}
