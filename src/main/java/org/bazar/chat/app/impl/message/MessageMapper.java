package org.bazar.chat.app.impl.message;

import org.bazar.chat.app.api.message.dto.CreateMessageDto;
import org.bazar.chat.app.api.message.dto.GetMessageDto;
import org.bazar.chat.app.api.message.dto.MessageCreatedEvent;
import org.bazar.chat.domain.message.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mapping(target = "chatId", source = "message.chat.id")
    GetMessageDto toGetMessageDto(Message message, boolean isDeletable);

    Message toMessage(CreateMessageDto dto);

    @Mapping(target = "chatId", source = "chat.id")
    MessageCreatedEvent toMessageCreatedEvent(Message message);
}
