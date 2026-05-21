package org.bazar.chat.app.impl.reaction;

import org.bazar.chat.app.api.reaction.dto.GetReactionDto;
import org.bazar.chat.domain.reaction.Reaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ReactionMapper {
    @Mapping(source = "id", target = "reactionId")
    GetReactionDto toGetReactionDto(Reaction reaction);

    List<GetReactionDto> toGetReactionDtoList(List<Reaction> reaction);
}
