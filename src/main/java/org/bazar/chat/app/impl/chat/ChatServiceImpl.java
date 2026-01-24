package org.bazar.chat.app.impl.chat;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.chat.ChatRepository;
import org.bazar.chat.app.api.chat.ChatService;
import org.bazar.chat.app.api.message.MessageRepository;
import org.bazar.chat.app.api.chat.dto.GetChatDto;
import org.bazar.chat.domain.chat.Chat;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final ChatMapper chatMapper;

    @Override
    @Transactional
    public GetChatDto createChat(Long spaceId) {
        Chat chat = chatMapper.mapToChat(spaceId);
        return chatMapper.mapToGetChatDto(chatRepository.save(chat));
    }

    @Override
    public GetChatDto getChatBySpaceId(Long spaceId) {
        return chatMapper.mapToGetChatDto(chatRepository.findBySpaceId(spaceId));
    }

    @Override
    @Transactional
    public void deleteChatBySpaceId(Long spaceId) {
        Chat chat = chatRepository.findBySpaceId(spaceId);
        messageRepository.deleteAllByChat(chat);
        chatRepository.delete(chat);
    }
}
