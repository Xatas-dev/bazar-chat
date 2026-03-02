package org.bazar.chat.adapter.inbound.rest.chat;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.api.ChatsApi;
import org.bazar.chat.app.api.chat.CreateChatInbound;
import org.bazar.chat.app.api.chat.GetChatBySpaceIdInbound;
import org.bazar.chat.app.api.chat.dto.GetChatDto;
import org.bazar.chat.model.ChatResponse;
import org.bazar.chat.model.CreateChatRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController implements ChatsApi {
    private final RestChatMapper chatMapper;
    private final CreateChatInbound createChatInbound;
    private final GetChatBySpaceIdInbound getChatBySpaceIdInbound;

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
}
