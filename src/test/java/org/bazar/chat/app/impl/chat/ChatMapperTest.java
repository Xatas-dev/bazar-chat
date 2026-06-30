package org.bazar.chat.app.impl.chat;

import org.bazar.chat.AbstractTest;
import org.bazar.chat.app.api.chat.dto.GetChatDto;
import org.bazar.chat.domain.chat.Chat;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.bazar.chat.TestDataTransformUtil.readObjectFromFile;

public class ChatMapperTest extends AbstractTest {
    private final ChatMapper chatMapper = Mappers.getMapper(ChatMapper.class);

    @Test
    void mapToChat() {
        Chat result = chatMapper.mapToChat(5L);

        assertEqualsToFile(result, "/ChatMapperTest/Chat_expected.json");
    }

    @Test
    void mapToGetChatDto() {
        Chat chat = readObjectFromFile("/ChatMapperTest/Chat.json", Chat.class);

        GetChatDto result = chatMapper.mapToGetChatDto(chat);

        assertEqualsToFile(result, "/ChatMapperTest/GetChatDto_expected.json");
    }
}
