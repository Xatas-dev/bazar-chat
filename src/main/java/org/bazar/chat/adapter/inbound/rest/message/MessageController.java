package org.bazar.chat.adapter.inbound.rest.message;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.api.MessagesApi;
import org.bazar.chat.app.api.message.MessageService;
import org.bazar.chat.app.api.message.dto.CreateMessageDto;
import org.bazar.chat.app.api.message.dto.GetMessagePageDto;
import org.bazar.chat.model.CreateMessageRequest;
import org.bazar.chat.model.MessagePageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController implements MessagesApi {
    private final MessageService messageService;
    private final RestMessageMapper mapper;

    @Override
    public ResponseEntity<Void> createMessage(Long chatId, CreateMessageRequest createMessageRequest) {
        CreateMessageDto createMessageDto = mapper.toCreateMessageDto(chatId, createMessageRequest);
        messageService.createMessage(createMessageDto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<MessagePageResponse> getChatMessages(Long chatId, @PageableDefault(size = 20) Pageable pageable) {
        GetMessagePageDto messagesPage = messageService.getChatMessages(chatId, pageable);
        return ResponseEntity.ok(mapper.toMessageResponse(messagesPage));
    }
}
