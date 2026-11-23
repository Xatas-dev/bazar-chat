package org.bazar.chat.app.impl.chat;

import org.bazar.chat.app.api.chat.dto.GetChatDto;
import org.bazar.chat.domain.chat.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ChatMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "encryptedDek", ignore = true)
    @Mapping(target = "dekIv", ignore = true)
    @Mapping(target = "kekVersion", ignore = true)
    Chat mapToChat(Long spaceId);

    GetChatDto mapToGetChatDto(Chat chat);
}
