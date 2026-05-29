package org.bazar.chat.adapter.inbound.rest.reaction;

import org.bazar.chat.app.api.reaction.dto.MessageReactionListDto;
import org.bazar.chat.model.MessageReactionListResponse;
import org.mapstruct.Mapper;

@Mapper
public interface RestReactionMapper {
    MessageReactionListResponse toMessageReactionListResponse(MessageReactionListDto messageReactionList);

}
