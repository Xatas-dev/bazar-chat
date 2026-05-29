package org.bazar.chat.adapter.inbound.rest.chat;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.api.ChatsApi;
import org.bazar.chat.app.api.chat.CreateChatInbound;
import org.bazar.chat.app.api.chat.GetChatBySpaceIdInbound;
import org.bazar.chat.app.api.chat.dto.GetChatDto;
import org.bazar.chat.app.api.reaction.GetChatReactionsInbound;
import org.bazar.chat.app.api.reaction.dto.GetReactionDto;
import org.bazar.chat.model.ChatResponse;
import org.bazar.chat.model.CreateChatRequest;
import org.bazar.chat.model.ReactionListResponse;
import org.bazar.chat.model.ReactionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController implements ChatsApi {
    private final RestChatMapper chatMapper;
    private final CreateChatInbound createChatInbound;
    private final GetChatBySpaceIdInbound getChatBySpaceIdInbound;
    private final GetChatReactionsInbound getChatReactionsInbound;

    @Override
    public ResponseEntity<ChatResponse> createChat(CreateChatRequest createChatRequest) {
        GetChatDto chat = createChatInbound.execute(createChatRequest.getSpaceId());
        return ResponseEntity.ok(chatMapper.mapToChatResponse(chat));
    }

    @Override
    public ResponseEntity<ChatResponse> getChatBySpace(Long spaceId) {
        GetChatDto chat = getChatBySpaceIdInbound.execute(spaceId);
        return ResponseEntity.ok(chatMapper.mapToChatResponse(chat));
    }

    @Override
    public ResponseEntity<ReactionListResponse> getChatReactions(Long chatId) {
        List<GetReactionDto> reactions = getChatReactionsInbound.execute();
        List<ReactionResponse> reactionResponseList = reactions.stream().map(chatMapper::mapToReactionResponse).toList();
        return ResponseEntity.ok(new ReactionListResponse(reactionResponseList));
    }
}
