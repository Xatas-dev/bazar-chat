package org.bazar.chat.app.impl.chat;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.chat.ChatRepository;
import org.bazar.chat.app.api.chat.CreateChatInbound;
import org.bazar.chat.app.api.chat.dto.GetChatDto;
import org.bazar.chat.domain.chat.Chat;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация интерфейса для создания чата
 */
@Component
@RequiredArgsConstructor
public class CreateChatUseCase implements CreateChatInbound {
    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;

    @Override
    @Transactional
    public GetChatDto execute(Long spaceId) {
        Chat chat = chatMapper.mapToChat(spaceId);
        return chatMapper.mapToGetChatDto(chatRepository.save(chat));
    }
}
