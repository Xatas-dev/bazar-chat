package org.bazar.chat.app.impl.message;

import org.bazar.chat.app.api.persona.model.UserDto;
import org.bazar.chat.app.api.message.dto.AuthorDto;
import org.bazar.chat.app.api.message.dto.AuthorStatus;
import org.bazar.chat.app.api.message.dto.CreateMessageDto;
import org.bazar.chat.app.api.message.dto.GetMessageDto;
import org.bazar.chat.app.api.message.dto.event.MessageCreatedEvent;
import org.bazar.chat.app.api.message.dto.event.MessageDeletedEvent;
import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.domain.message.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper
public interface MessageMapper {
    @Mapping(target = "chatId", source = "message.chat.id")
    @Mapping(target = "author", expression = "java(toAuthorDto(userDto, status, message))")
    GetMessageDto toGetMessageDto(Message message, boolean isDeletable, UserDto userDto, AuthorStatus status);

    @Mapping(target = "userId", source = "message.userId")
    AuthorDto toAuthorDto(UserDto userDto, AuthorStatus status, Message message);

    @Mapping(target = "content", source = "dto.content")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "visible", ignore = true)
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "chat", source = "chat")
    @Mapping(target = "replyMessage", source = "replyMessage")
    Message toMessage(CreateMessageDto dto, Message replyMessage, Chat chat, UUID userId);

    @Mapping(target = "chatId", source = "message.chat.id")
    @Mapping(target = "author", expression = "java(toAuthorDto(userDto, status, message))")
    MessageCreatedEvent toMessageCreatedEvent(Message message, UserDto userDto, AuthorStatus status);

    default MessageDeletedEvent toMessageDeletedEvent(Long chatId, List<Long> messageIds) {
        return new MessageDeletedEvent(chatId, messageIds);
    }
}
