package org.bazar.chat.app.impl.reaction;

import org.bazar.chat.app.api.reaction.dto.GetReactionDto;
import org.bazar.chat.domain.reaction.Reaction;
import org.bazar.chat.app.api.persona.model.UserDto;
import org.bazar.chat.app.api.reaction.dto.ReactionUserDto;
import org.bazar.chat.app.api.reaction.dto.UserStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ReactionMapper {
    @Mapping(source = "id", target = "reactionId")
    GetReactionDto toGetReactionDto(Reaction reaction);

    List<GetReactionDto> toGetReactionDtoList(List<Reaction> reaction);

    @Mapping(target = "userId", source = "userDto.userId")
    @Mapping(target = "firstName", source = "userDto.firstName")
    @Mapping(target = "lastName", source = "userDto.lastName")
    @Mapping(target = "status", source = "status")
    ReactionUserDto toReactionUserDto(UserDto userDto, UserStatus status);
}
