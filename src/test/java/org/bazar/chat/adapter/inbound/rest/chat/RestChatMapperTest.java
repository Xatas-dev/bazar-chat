package org.bazar.chat.adapter.inbound.rest.chat;

import org.bazar.chat.AbstractTest;
import org.bazar.chat.adapter.inbound.rest.chat.dto.V1CreateChatResponse;
import org.bazar.chat.adapter.inbound.rest.chat.dto.V1GetChatResponse;
import org.bazar.chat.adapter.inbound.rest.chat.dto.V1ReactionResponse;
import org.bazar.chat.app.api.chat.dto.GetChatDto;
import org.bazar.chat.app.api.reaction.dto.GetReactionDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.bazar.chat.TestDataTransformUtil.readObjectFromFile;

public class RestChatMapperTest extends AbstractTest {
    private final RestChatMapper restChatMapper = Mappers.getMapper(RestChatMapper.class);

    @Test
    void mapToV1CreateChatResponse() {
        GetChatDto getChatDto = readObjectFromFile("/RestChatMapperTest/GetChatDto.json", GetChatDto.class);

        V1CreateChatResponse result = restChatMapper.mapToV1CreateChatResponse(getChatDto);

        assertEqualsToFile(result, "/RestChatMapperTest/V1CreateChatResponse_expected.json");
    }

    @Test
    void mapToV1GetChatResponse() {
        GetChatDto getChatDto = readObjectFromFile("/RestChatMapperTest/GetChatDto.json", GetChatDto.class);

        V1GetChatResponse result = restChatMapper.mapToV1GetChatResponse(getChatDto);

        assertEqualsToFile(result, "/RestChatMapperTest/V1GetChatResponse_expected.json");
    }

    @Test
    void mapToV1ReactionResponse() {
        GetReactionDto getReactionDto = readObjectFromFile("/RestChatMapperTest/GetReactionDto.json", GetReactionDto.class);

        V1ReactionResponse result = restChatMapper.mapToV1ReactionResponse(getReactionDto);

        assertEqualsToFile(result, "/RestChatMapperTest/V1ReactionResponse_expected.json");
    }
}
