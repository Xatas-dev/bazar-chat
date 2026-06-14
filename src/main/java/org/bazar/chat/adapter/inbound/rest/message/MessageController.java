package org.bazar.chat.adapter.inbound.rest.message;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.adapter.inbound.rest.message.dto.V1CreateMessageRequest;
import org.bazar.chat.adapter.inbound.rest.message.dto.V1DeleteMessageRequest;
import org.bazar.chat.adapter.inbound.rest.message.dto.V1GetMessageResponse;
import org.bazar.chat.adapter.inbound.rest.message.dto.V1UpdateChatMessageRequest;
import org.bazar.chat.app.api.message.CreateMessageInbound;
import org.bazar.chat.app.api.message.DeleteMessagesInbound;
import org.bazar.chat.app.api.message.EditMessageContentInbound;
import org.bazar.chat.app.api.message.GetChatMessagesInbound;
import org.bazar.chat.app.api.message.dto.CreateMessageDto;
import org.bazar.chat.app.api.message.dto.GetMessageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/spaces/{spaceId}/chats/{chatId}/messages")
public class MessageController implements MessageControllerSwagger {
    private final RestMessageMapper restMessageMapper;
    private final GetChatMessagesInbound getChatMessagesInbound;
    private final CreateMessageInbound createMessageInbound;
    private final DeleteMessagesInbound deleteMessagesInbound;
    private final EditMessageContentInbound editMessageContentInbound;

    @PostMapping
    public ResponseEntity<Void> createMessage(@PathVariable Long spaceId, @PathVariable Long chatId,
                                              @RequestBody V1CreateMessageRequest createMessageRequest) {
        CreateMessageDto createMessageDto = restMessageMapper.toCreateMessageDto(chatId, createMessageRequest);
        createMessageInbound.execute(createMessageDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteChatMessageById(@PathVariable Long spaceId, @PathVariable Long chatId,
                                                      @RequestBody V1DeleteMessageRequest deleteMessageRequest) {
        deleteMessagesInbound.execute(chatId, deleteMessageRequest.messageIds());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Page<V1GetMessageResponse> getChatMessages(@PathVariable Long spaceId, @PathVariable Long chatId,
                                                      @PageableDefault(size = 20) Pageable pageable) {
        Page<GetMessageDto> messages = getChatMessagesInbound.execute(chatId, pageable);
        return messages.map(restMessageMapper::toV1GetMessageResponse);
    }

    @PatchMapping("/{messageId}")
    public ResponseEntity<Void> updateChatMessage(@PathVariable Long spaceId, @PathVariable Long chatId,
                                                  @PathVariable Long messageId,
                                                  @RequestBody V1UpdateChatMessageRequest updateChatMessageRequest) {
        editMessageContentInbound.execute(restMessageMapper.toUpdateMessageDto(chatId, messageId, updateChatMessageRequest.newContent()));
        return ResponseEntity.ok().build();
    }
}
