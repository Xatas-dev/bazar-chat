package org.bazar.chat.app.api.chat;

import org.bazar.chat.app.api.chat.dto.GetChatDto;

public interface ChatService {
    GetChatDto createChat(Long spaceId);

    GetChatDto getChatBySpaceId(Long spaceId);

    void deleteChatBySpaceId(Long spaceId);
}
