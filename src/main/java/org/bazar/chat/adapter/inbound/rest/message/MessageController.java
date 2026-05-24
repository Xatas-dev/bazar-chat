package org.bazar.chat.adapter.inbound.rest.message;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.api.MessagesApi;
import org.bazar.chat.app.api.message.CreateMessageInbound;
import org.bazar.chat.app.api.message.DeleteMessagesInbound;
import org.bazar.chat.app.api.message.EditMessageContentInbound;
import org.bazar.chat.app.api.message.GetChatMessagesInbound;
import org.bazar.chat.app.api.message.dto.CreateMessageDto;
import org.bazar.chat.app.api.message.dto.GetMessagePageDto;
import org.bazar.chat.app.api.reaction.UpdateMessageReactionInbound;
import org.bazar.chat.app.api.reaction.dto.UpdateReactionDto;
import org.bazar.chat.model.CreateMessageRequest;
import org.bazar.chat.model.DeleteMessageRequest;
import org.bazar.chat.model.MessagePageResponse;
import org.bazar.chat.model.ReactionUpdateResponse;
import org.bazar.chat.model.UpdateChatMessageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController implements MessagesApi {
    private final RestMessageMapper restMessageMapper;
    private final GetChatMessagesInbound getChatMessagesInbound;
    private final CreateMessageInbound createMessageInbound;
    private final DeleteMessagesInbound deleteMessagesInbound;
    private final EditMessageContentInbound editMessageContentInbound;
    private final UpdateMessageReactionInbound updateMessageReactionInbound;

    @Override
    public ResponseEntity<Void> createMessage(Long chatId, CreateMessageRequest createMessageRequest) {
        CreateMessageDto createMessageDto = restMessageMapper.toCreateMessageDto(chatId, createMessageRequest);
        createMessageInbound.execute(createMessageDto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteChatMessageById(Long chatId, DeleteMessageRequest deleteMessageRequest) {
        deleteMessagesInbound.execute(chatId, deleteMessageRequest.getMessageIds());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<MessagePageResponse> getChatMessages(Long chatId, @PageableDefault(size = 20) Pageable pageable) {
        GetMessagePageDto messagesPage = getChatMessagesInbound.execute(chatId, pageable);
        return ResponseEntity.ok(restMessageMapper.toMessageResponse(messagesPage));
    }

    @Override
    public ResponseEntity<Void> updateChatMessage(String chatId, String messageId, UpdateChatMessageRequest updateChatMessageRequest) {
        editMessageContentInbound.execute(restMessageMapper.toUpdateMessageDto(chatId, messageId, updateChatMessageRequest.getNewContent()));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<ReactionUpdateResponse> updateMessageReaction(Long chatId, Long messageId, Long reactionId) {
        UpdateReactionDto result = updateMessageReactionInbound.execute(chatId, messageId, reactionId);
        return ResponseEntity.ok().body(new ReactionUpdateResponse(result.messageId(), result.reactionId(), result.count()));
    }
}
