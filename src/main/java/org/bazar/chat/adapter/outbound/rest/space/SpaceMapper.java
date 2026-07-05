package org.bazar.chat.adapter.outbound.rest.space;

import org.bazar.chat.adapter.outbound.rest.space.dto.SpaceUserResponse;
import org.bazar.chat.app.api.space.dto.SpaceUserDto;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper
public interface SpaceMapper {
    Set<SpaceUserDto> toSpaceUserDtoSet(Set<SpaceUserResponse.UserInSpaceDto> response);
}
