package org.bazar.chat.adapter.inbound.rest.chat;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.api.ChatsApi;
import org.bazar.chat.app.api.chat.ChatService;
import org.bazar.chat.app.api.chat.dto.GetChatDto;
import org.bazar.chat.model.ChatResponse;
import org.bazar.chat.model.CreateChatRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController implements ChatsApi {
    private final ChatService chatService;
    private final RestChatMapper chatMapper;

    @Override
    public ResponseEntity<ChatResponse> createChat(CreateChatRequest createChatRequest) {
        GetChatDto chat = chatService.createChat(createChatRequest.getSpaceId());
        return ResponseEntity.ok(chatMapper.mapToChatResponse(chat));
    }

    @Override
    public ResponseEntity<ChatResponse> getChatBySpace(Long spaceId) {
        GetChatDto chat = chatService.getChatBySpaceId(spaceId);
        return ResponseEntity.ok(chatMapper.mapToChatResponse(chat));
    }
}
