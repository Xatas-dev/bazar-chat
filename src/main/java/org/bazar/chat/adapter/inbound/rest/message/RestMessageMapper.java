package org.bazar.chat.adapter.inbound.rest.message;

import org.bazar.chat.app.api.message.dto.CreateMessageDto;
import org.bazar.chat.app.api.message.dto.GetMessagePageDto;
import org.bazar.chat.model.CreateMessageRequest;
import org.bazar.chat.model.MessagePageResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestMessageMapper {
    MessagePageResponse toMessageResponse(GetMessagePageDto message);

    CreateMessageDto toCreateMessageDto(Long chatId, CreateMessageRequest createMessageRequest);
}
