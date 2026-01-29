package org.bazar.chat.app.impl.message;

import org.bazar.chat.app.api.message.dto.CreateMessageDto;
import org.bazar.chat.app.api.message.dto.GetMessageDto;
import org.bazar.chat.app.api.message.dto.event.MessageCreatedEvent;
import org.bazar.chat.app.api.message.dto.event.MessageDeletedEvent;
import org.bazar.chat.domain.message.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mapping(target = "chatId", source = "message.chat.id")
    GetMessageDto toGetMessageDto(Message message, boolean isDeletable);

    Message toMessage(CreateMessageDto dto);

    MessageCreatedEvent toMessageCreatedEvent(Message message);

    default MessageDeletedEvent toMessageDeletedEvent(List<Long> messageIds) {
        return new MessageDeletedEvent(messageIds);
    }
}
