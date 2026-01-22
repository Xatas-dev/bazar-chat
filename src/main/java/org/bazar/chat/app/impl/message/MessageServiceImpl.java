package org.bazar.chat.app.impl.message;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.chat.ChatRepository;
import org.bazar.chat.app.api.message.MessageRepository;
import org.bazar.chat.app.api.message.MessageService;
import org.bazar.chat.app.api.message.dto.CreateMessageDto;
import org.bazar.chat.app.api.message.dto.GetMessagePageDto;
import org.bazar.chat.app.impl.helpers.SecurityContextHelper;
import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.domain.message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final MessageMapper mapper;
    private final SecurityContextHelper securityContextHelper;

    @Override
    public GetMessagePageDto getChatMessages(Long chatId, Pageable pageable) {
        Page<Message> messages = messageRepository.findAllByChatId(chatId, pageable);
        return mapper.toGetMessagePageDto(messages);
    }

    @Override
    @Transactional
    public void createMessage(CreateMessageDto dto) {
        Chat chat = chatRepository.findByChatId(dto.chatId());
        Message message = mapper.toMessage(dto);
        message.setChat(chat);
        message.setUserId(securityContextHelper.getAuthenticatedUserId());
        messageRepository.save(message);
    }
}
