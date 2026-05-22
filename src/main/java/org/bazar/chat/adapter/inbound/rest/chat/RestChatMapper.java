package org.bazar.chat.adapter.inbound.rest.chat;

import org.bazar.chat.app.api.chat.dto.GetChatDto;
import org.bazar.chat.app.api.reaction.dto.GetReactionDto;
import org.bazar.chat.model.ChatResponse;
import org.bazar.chat.model.ReactionResponse;
import org.mapstruct.Mapper;

@Mapper
public interface RestChatMapper {
    ChatResponse mapToChatResponse(GetChatDto chat);


    ReactionResponse mapToReactionResponse(GetReactionDto reaction);
}
