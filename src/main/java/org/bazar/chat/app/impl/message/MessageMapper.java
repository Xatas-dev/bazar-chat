package org.bazar.chat.app.impl.message;

import org.bazar.chat.app.api.message.dto.CreateMessageDto;
import org.bazar.chat.app.api.message.dto.GetMessageDto;
import org.bazar.chat.app.api.message.dto.GetMessagePageDto;
import org.bazar.chat.app.api.message.dto.MessageCreatedEvent;
import org.bazar.chat.domain.message.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mapping(target = "chatId", source = "chat.id")
    GetMessageDto toGetMessageDto(Message message);

    default GetMessagePageDto toGetMessagePageDto(Page<Message> messages) {
        List<GetMessageDto> pageContent = messages.getContent().stream()
                .map(this::toGetMessageDto)
                .toList();
        return new GetMessagePageDto(
                pageContent, messages.getNumber(), messages.getSize(), messages.getTotalElements(), messages.getTotalPages()
        );
    }

    Message toMessage(CreateMessageDto dto);

    @Mapping(target = "chatId", source = "chat.id")
    MessageCreatedEvent toMessageCreatedEvent(Message message);
}
