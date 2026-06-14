package org.bazar.chat.adapter.inbound.rest.message;

import io.swagger.v3.oas.annotations.Operation;
import org.bazar.chat.adapter.inbound.rest.message.dto.V1CreateMessageRequest;
import org.bazar.chat.adapter.inbound.rest.message.dto.V1DeleteMessageRequest;
import org.bazar.chat.adapter.inbound.rest.message.dto.V1GetMessageResponse;
import org.bazar.chat.adapter.inbound.rest.message.dto.V1UpdateChatMessageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface MessageControllerSwagger {
    @Operation(summary = "Create a message", description = "Send a new message to a specific chat")
    ResponseEntity<Void> createMessage(Long spaceId, Long chatId, V1CreateMessageRequest createMessageRequest);

    @Operation(summary = "Delete messages by ids")
    ResponseEntity<Void> deleteChatMessageById(Long spaceId, Long chatId, V1DeleteMessageRequest deleteMessageRequest);

    @Operation(summary = "Get all messages in chat", description = "Retrieve paginated message history")
    Page<V1GetMessageResponse> getChatMessages(Long spaceId, Long chatId, Pageable pageable);

    @Operation(summary = "Update message content", description = "Update the content of a specific message in a chat")
    ResponseEntity<Void> updateChatMessage(Long spaceId, Long chatId, Long messageId, V1UpdateChatMessageRequest updateChatMessageRequest);
}
