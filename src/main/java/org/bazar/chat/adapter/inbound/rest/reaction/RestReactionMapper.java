package org.bazar.chat.adapter.inbound.rest.reaction;

import org.bazar.chat.adapter.inbound.rest.reaction.dto.V1MessageReactionListResponse;
import org.bazar.chat.adapter.inbound.rest.reaction.dto.V1ReactionUpdateResponse;
import org.bazar.chat.app.api.reaction.dto.MessageReactionListDto;
import org.bazar.chat.app.api.reaction.dto.UpdatedReactionDto;
import org.bazar.chat.app.api.reaction.dto.UpdatedReactionsDto;
import org.bazar.chat.model.MessageReactionListResponse;
import org.bazar.chat.model.ReactionUpdateResponse;
import org.bazar.chat.model.UpdatedReaction;
import org.bazar.chat.app.api.reaction.dto.UpdateReactionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RestReactionMapper {
    V1MessageReactionListResponse toV1MessageReactionListResponse(MessageReactionListDto messageReactionList);

    @Mapping(target = "messageId", source = "messageId")
    @Mapping(target = "updatedReactions", source = "updatedReactions")
    ReactionUpdateResponse toV1ReactionUpdateResponse(UpdatedReactionsDto updatedReactionsDto);

    UpdatedReaction toUpdatedReaction(UpdatedReactionDto updatedReactionDto);

    default String map(Long value) { return value == null ? null : value.toString(); }
}
