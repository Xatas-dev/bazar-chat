package org.bazar.chat.app.impl.message;

import org.bazar.chat.app.api.persona.model.UserDto;
import org.bazar.chat.app.api.message.dto.AuthorDto;
import org.bazar.chat.app.api.message.dto.AuthorStatus;
import org.bazar.chat.app.api.message.dto.CreateMessageDto;
import org.bazar.chat.app.api.message.dto.GetMessageDto;
import org.bazar.chat.app.api.message.dto.event.MessageCreatedEvent;
import org.bazar.chat.app.api.message.dto.event.MessageDeletedEvent;
import org.bazar.chat.domain.message.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MessageMapper {
    @Mapping(target = "chatId", source = "message.chat.id")
    @Mapping(target = "author", expression = "java(toAuthorDto(userDto, status, message))")
    GetMessageDto toGetMessageDto(Message message, boolean isDeletable, UserDto userDto, AuthorStatus status);

    @Mapping(target = "userId", source = "message.userId")
    AuthorDto toAuthorDto(UserDto userDto, AuthorStatus status, Message message);

    Message toMessage(CreateMessageDto dto);

    @Mapping(target = "chatId", source = "message.chat.id")
    @Mapping(target = "author", expression = "java(toAuthorDto(userDto, status, message))")
    MessageCreatedEvent toMessageCreatedEvent(Message message, UserDto userDto, AuthorStatus status);

    default MessageDeletedEvent toMessageDeletedEvent(Long chatId, List<Long> messageIds) {
        return new MessageDeletedEvent(chatId, messageIds);
    }
}
