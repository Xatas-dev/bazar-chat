package org.bazar.chat.adapter.inbound.rest.chat;

import org.bazar.chat.adapter.inbound.rest.chat.dto.V1CreateChatResponse;
import org.bazar.chat.adapter.inbound.rest.chat.dto.V1GetChatResponse;
import org.bazar.chat.adapter.inbound.rest.chat.dto.V1ReactionResponse;
import org.bazar.chat.app.api.chat.dto.GetChatDto;
import org.bazar.chat.app.api.reaction.dto.GetReactionDto;
import org.mapstruct.Mapper;

@Mapper
public interface RestChatMapper {
    V1CreateChatResponse mapToV1CreateChatResponse(GetChatDto chat);

    V1GetChatResponse mapToV1GetChatResponse(GetChatDto chat);

    V1ReactionResponse mapToV1ReactionResponse(GetReactionDto reaction);
}
