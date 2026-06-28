package org.bazar.chat.app.impl.chat;

import org.bazar.chat.AbstractTest;
import org.bazar.chat.TestDataTransformUtil;
import org.bazar.chat.app.api.chat.dto.GetChatDto;
import org.bazar.chat.domain.chat.Chat;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class ChatMapperTest extends AbstractTest {
    private final ChatMapper chatMapper = Mappers.getMapper(ChatMapper.class);

    @Test
    void mapToChat() {
        Chat chat = chatMapper.mapToChat(5L);

        assertEqualsToFile(chat, "/ChatMapperTest/Chat_fromDto.json");
    }

    @Test
    void mapToGetChatDto() {
        Chat chat = TestDataTransformUtil.readObjectFromFile("/ChatMapperTest/Chat.json", Chat.class);

        GetChatDto getChatDto = chatMapper.mapToGetChatDto(chat);

        assertEqualsToFile(getChatDto, "/ChatMapperTest/GetChatDto.json");
    }
}
