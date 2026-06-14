package org.bazar.chat.adapter.inbound.rest.chat;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.adapter.inbound.rest.chat.dto.V1CreateChatResponse;
import org.bazar.chat.adapter.inbound.rest.chat.dto.V1GetChatResponse;
import org.bazar.chat.adapter.inbound.rest.chat.dto.V1ReactionListResponse;
import org.bazar.chat.adapter.inbound.rest.chat.dto.V1ReactionResponse;
import org.bazar.chat.app.api.chat.CreateChatInbound;
import org.bazar.chat.app.api.chat.GetChatBySpaceIdInbound;
import org.bazar.chat.app.api.chat.dto.GetChatDto;
import org.bazar.chat.app.api.reaction.GetChatReactionsInbound;
import org.bazar.chat.app.api.reaction.dto.GetReactionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/spaces/{spaceId}/chats")
public class ChatController implements ChatControllerSwagger {
    private final RestChatMapper chatMapper;
    private final CreateChatInbound createChatInbound;
    private final GetChatBySpaceIdInbound getChatBySpaceIdInbound;
    private final GetChatReactionsInbound getChatReactionsInbound;

    @PostMapping
    public ResponseEntity<V1CreateChatResponse> createChat(@PathVariable String spaceId) {
        GetChatDto chat = createChatInbound.execute(Long.parseLong(spaceId));
        return ResponseEntity.ok(chatMapper.mapToV1CreateChatResponse(chat));
    }

    @GetMapping
    public ResponseEntity<V1GetChatResponse> getChatBySpace(@PathVariable String spaceId) {
        GetChatDto chat = getChatBySpaceIdInbound.execute(Long.parseLong(spaceId));
        return ResponseEntity.ok(chatMapper.mapToV1GetChatResponse(chat));
    }

    @GetMapping("/{chatId}/reactions")
    public ResponseEntity<V1ReactionListResponse> getChatReactions(@PathVariable String spaceId, @PathVariable String chatId) {
        List<GetReactionDto> reactions = getChatReactionsInbound.execute();
        List<V1ReactionResponse> v1ReactionResponseList = reactions.stream().map(chatMapper::mapToV1ReactionResponse).toList();
        return ResponseEntity.ok(new V1ReactionListResponse(v1ReactionResponseList));
    }
}
