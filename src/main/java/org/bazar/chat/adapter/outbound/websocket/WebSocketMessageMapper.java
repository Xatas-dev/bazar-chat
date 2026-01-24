package org.bazar.chat.adapter.outbound.websocket;

import org.bazar.chat.adapter.outbound.websocket.dto.WebSocketMessageCreatedEvent;
import org.bazar.chat.app.api.message.dto.MessageCreatedEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WebSocketMessageMapper {
    WebSocketMessageCreatedEvent mapToWebSocketMessageCreatedEvent(MessageCreatedEvent event);
}
