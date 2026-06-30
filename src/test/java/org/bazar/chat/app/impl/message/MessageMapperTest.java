package org.bazar.chat.app.impl.message;

import com.fasterxml.jackson.core.type.TypeReference;
import org.bazar.chat.AbstractTest;
import org.bazar.chat.app.api.message.dto.*;
import org.bazar.chat.app.api.message.dto.event.MessageCreatedEvent;
import org.bazar.chat.app.api.message.dto.event.MessageDeletedEvent;
import org.bazar.chat.app.api.message.dto.event.MessageEditedEvent;
import org.bazar.chat.app.api.persona.model.UserDto;
import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.domain.message.Message;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.bazar.chat.TestDataTransformUtil.readObjectFromFile;
import static org.bazar.chat.app.api.message.dto.AllowedActions.DELETE;
import static org.bazar.chat.app.api.message.dto.AllowedActions.EDIT;
import static org.bazar.chat.app.api.message.dto.AuthorStatus.EXIST;
import static org.bazar.chat.app.api.message.dto.AuthorStatus.UNKNOWN;

public class MessageMapperTest extends AbstractTest {
    private final MessageMapper messageMapper = Mappers.getMapper(MessageMapper.class);

    @Test
    void toGetMessageDto() {
        Message message = readObjectFromFile("/MessageMapperTest/Message.json", Message.class);
        List<MessageReactionDto> messageReactionDtoList =
                readObjectFromFile("/MessageMapperTest/MessageReactionDtoList.json", new TypeReference<>() {});
        ReplyMessageDto replyMessageDto =
                readObjectFromFile("/MessageMapperTest/ReplyMessageDto.json", ReplyMessageDto.class);
        UserDto userDto = readObjectFromFile("/MessageMapperTest/UserDto.json", UserDto.class);

        GetMessageDto result = messageMapper.toGetMessageDto(
                message,
                List.of(DELETE, EDIT),
                userDto,
                EXIST,
                replyMessageDto,
                messageReactionDtoList
        );

        assertEqualsToFile(result, "/MessageMapperTest/GetMessageDto.json");
    }

    @Test
    void toReplyMessageDto() {
        Message message = readObjectFromFile("/MessageMapperTest/Message.json", Message.class);
        UserDto userDto = readObjectFromFile("/MessageMapperTest/UserDto.json", UserDto.class);

        ReplyMessageDto result = messageMapper.toReplyMessageDto(message, userDto, EXIST, "preview");

        assertEqualsToFile(result, "/MessageMapperTest/ReplyMessageDto_expected.json");
    }

    @Test
    void toAuthorDto_withMessage() {
        Message message = readObjectFromFile("/MessageMapperTest/Message.json", Message.class);
        UserDto userDto = readObjectFromFile("/MessageMapperTest/UserDto.json", UserDto.class);

        AuthorDto result = messageMapper.toAuthorDto(userDto, UNKNOWN, message);

        assertEqualsToFile(result, "/MessageMapperTest/AuthorDto_expected_withMessage.json");
    }

    @Test
    void toAuthorDto_withUserId() {
        UserDto userDto = readObjectFromFile("/MessageMapperTest/UserDto.json", UserDto.class);

        AuthorDto result = messageMapper.toAuthorDto(userDto, UNKNOWN, UUID.fromString("2d6b010f-d917-4e6a-bb21-9a656d4be9f2"));

        assertEqualsToFile(result, "/MessageMapperTest/AuthorDto_expected_withUserId.json");
    }

    @Test
    void toMessage() {
        CreateMessageDto createMessageDto =
                readObjectFromFile("/MessageMapperTest/CreateMessageDto.json", CreateMessageDto.class);
        Message replyMessage = readObjectFromFile("/MessageMapperTest/Message.json", Message.class);
        Chat chat = readObjectFromFile("/MessageMapperTest/Chat.json", Chat.class);

        Message result =
                messageMapper.toMessage(createMessageDto, replyMessage, chat, UUID.fromString("8885c424-e6e4-49cc-b2e7-71a4acfa9cfd"));

        assertEqualsToFile(result, "/MessageMapperTest/Message_expected.json");
    }

    @Test
    void toMessageCreatedEvent() {
        Message message = readObjectFromFile("/MessageMapperTest/Message.json", Message.class);
        UserDto userDto = readObjectFromFile("/MessageMapperTest/UserDto.json", UserDto.class);
        ReplyMessageDto replyMessageDto =
                readObjectFromFile("/MessageMapperTest/ReplyMessageDto.json", ReplyMessageDto.class);

        MessageCreatedEvent result = messageMapper.toMessageCreatedEvent(
                message, userDto, EXIST, replyMessageDto,
                List.of(DELETE), Set.of(UUID.fromString("8885c424-e6e4-49cc-b2e7-71a4acfa9cfd"))
        );

        assertEqualsToFile(result, "/MessageMapperTest/MessageCreatedEvent_expected.json");
    }

    @Test
    void toMessageDeletedEvent() {
        MessageDeletedEvent result = messageMapper.toMessageDeletedEvent(1L, List.of(2L, 3L));

        assertEqualsToFile(result, "/MessageMapperTest/MessageDeletedEvent_expected.json");
    }

    @Test
    void toMessageEditedEvent() {
        Message message = readObjectFromFile("/MessageMapperTest/Message.json", Message.class);

        MessageEditedEvent result = messageMapper.toMessageEditedEvent(message, "newContent");

        assertEqualsToFile(result, "/MessageMapperTest/MessageEditedEvent_expected.json");
    }
}
