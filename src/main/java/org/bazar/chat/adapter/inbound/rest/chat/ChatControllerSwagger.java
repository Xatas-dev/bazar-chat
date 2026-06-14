package org.bazar.chat.adapter.inbound.rest.chat;

import io.swagger.v3.oas.annotations.Operation;
import org.bazar.chat.adapter.inbound.rest.chat.dto.V1CreateChatResponse;
import org.bazar.chat.adapter.inbound.rest.chat.dto.V1GetChatResponse;
import org.bazar.chat.adapter.inbound.rest.chat.dto.V1ReactionListResponse;
import org.springframework.http.ResponseEntity;

public interface ChatControllerSwagger {
    @Operation(summary = "Create a chat",description = "Create a new chat linked to a Space")
    ResponseEntity<V1CreateChatResponse> createChat(String spaceId);

    @Operation(summary = "Get chats by Space ID", description = "Retrieve the chat id belonging to a specific Space")
    ResponseEntity<V1GetChatResponse> getChatBySpace(String spaceId);

    @Operation(summary = "Get reactions in chat", description = "Retrieve the list of available reactions for a chat")
    ResponseEntity<V1ReactionListResponse> getChatReactions(String spaceId, String chatId);
}
