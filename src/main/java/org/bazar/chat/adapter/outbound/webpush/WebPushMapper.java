package org.bazar.chat.adapter.outbound.webpush;

import org.bazar.chat.app.api.message.dto.event.MessageCreatedEvent;
import org.mapstruct.Mapper;

@Mapper
public interface WebPushMapper {
    PushPayloadDto toPushPayloadDto(MessageCreatedEvent event);
}
