package org.bazar.chat.adapter.outbound.rest.persona;

import org.bazar.chat.adapter.outbound.rest.persona.dto.PersonaUserResponse;
import org.bazar.chat.app.api.persona.model.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PersonaMapper {
    @Mapping(target = "userId", source = "id")
    UserDto mapToUserDto(PersonaUserResponse response);
}
