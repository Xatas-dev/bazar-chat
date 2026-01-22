package org.bazar.chat.adapter.inbound.rest.chat;

import org.bazar.chat.app.api.chat.dto.GetChatDto;
import org.bazar.chat.model.ChatResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestChatMapper {
    ChatResponse mapToChatResponse(GetChatDto chat);
}
