package org.bazar.chat.adapter.outbound.websocket;

import org.bazar.chat.adapter.outbound.websocket.dto.WebSocketMessageCreatedChatEvent;
import org.bazar.chat.adapter.outbound.websocket.dto.WebSocketMessageDeletedChatEvent;
import org.bazar.chat.adapter.outbound.websocket.dto.WebSocketMessageEditedChatEvent;
import org.bazar.chat.adapter.outbound.websocket.dto.payload.MessageCreatedPayload;
import org.bazar.chat.adapter.outbound.websocket.dto.payload.MessageDeletedPayload;
import org.bazar.chat.adapter.outbound.websocket.dto.payload.MessageEditedPayload;
import org.bazar.chat.app.api.message.dto.event.MessageCreatedEvent;
import org.bazar.chat.app.api.message.dto.event.MessageDeletedEvent;
import org.bazar.chat.app.api.message.dto.event.MessageEditedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface WebSocketMessageMapper {
    @Mapping(target = "payload", source = "event", qualifiedByName = "mapToMessageCreatedPayload")
    WebSocketMessageCreatedChatEvent map(MessageCreatedEvent event);

    @Named("mapToMessageCreatedPayload")
    MessageCreatedPayload mapToMessageCreatedPayload(MessageCreatedEvent event);

    @Mapping(target = "payload", source = "event", qualifiedByName = "mapToMessageDeletedPayload")
    WebSocketMessageDeletedChatEvent map(MessageDeletedEvent event);

    @Named("mapToMessageDeletedPayload")
    MessageDeletedPayload mapToMessageDeletedPayload(MessageDeletedEvent event);

    @Mapping(target = "payload", source = "event", qualifiedByName = "mapToMessageEditedPayload")
    WebSocketMessageEditedChatEvent map(MessageEditedEvent event);

    @Named("mapToMessageEditedPayload")
    MessageEditedPayload mapToMessageEditedPayload(MessageEditedEvent event);
}
