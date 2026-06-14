package org.bazar.chat.adapter.inbound.rest.message;

import org.bazar.chat.adapter.inbound.rest.message.dto.V1CreateMessageRequest;
import org.bazar.chat.adapter.inbound.rest.message.dto.V1GetMessageResponse;
import org.bazar.chat.app.api.message.dto.CreateMessageDto;
import org.bazar.chat.app.api.message.dto.GetMessageDto;
import org.bazar.chat.app.api.message.dto.UpdateMessageDto;
import org.mapstruct.Mapper;

@Mapper
public interface RestMessageMapper {
    V1GetMessageResponse toV1GetMessageResponse(GetMessageDto message);

    CreateMessageDto toCreateMessageDto(Long chatId, V1CreateMessageRequest createMessageRequest);

    UpdateMessageDto toUpdateMessageDto(Long chatId, Long messageId, String newContent);
}
