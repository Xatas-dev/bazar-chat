package org.bazar.chat.app.impl.chat;

import org.bazar.chat.app.api.chat.dto.GetChatDto;
import org.bazar.chat.domain.chat.Chat;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    Chat mapToChat(Long spaceId);

    GetChatDto mapToGetChatDto(Chat chat);
}
