package org.bazar.chat.adapter.outbound.websocket.dto;

import org.bazar.chat.app.api.message.dto.event.ChatEventType;

public interface WebSocketChatEvent {
    ChatEventType type();

    Long chatId();

    Object payload();
}
