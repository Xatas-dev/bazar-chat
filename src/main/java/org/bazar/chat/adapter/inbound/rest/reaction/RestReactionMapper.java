package org.bazar.chat.adapter.inbound.rest.reaction;

import org.bazar.chat.adapter.inbound.rest.reaction.dto.V1MessageReactionListResponse;
import org.bazar.chat.adapter.inbound.rest.reaction.dto.V1ReactionUpdateResponse;
import org.bazar.chat.app.api.reaction.dto.MessageReactionListDto;
import org.bazar.chat.app.api.reaction.dto.UpdateReactionDto;
import org.mapstruct.Mapper;

@Mapper
public interface RestReactionMapper {
    V1MessageReactionListResponse toV1MessageReactionListResponse(MessageReactionListDto messageReactionList);

    V1ReactionUpdateResponse toV1ReactionUpdateResponse(UpdateReactionDto updateReactionDto);
}
