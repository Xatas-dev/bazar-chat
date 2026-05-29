package org.bazar.chat.adapter.outbound.websocket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bazar.chat.adapter.outbound.websocket.dto.payload.ReactionChangedPayload;
import org.bazar.chat.app.api.message.dto.event.ChatEventType;

/**
 * Конкретная реализация события по изменению состояния реакции на сообщение
 *
 * @param chatId Идентификатор чата
 * @param payload Полезная нагрузка события
 */
public record WebSocketReactionChangedEvent(
        Long chatId,
        ReactionChangedPayload payload
) implements WebSocketChatEvent {
    @Override
    public ChatEventType type() {
        return ChatEventType.REACTION_CHANGED;
    }

    @JsonProperty
    public ChatEventType getType() {
        return type();
    }
}
