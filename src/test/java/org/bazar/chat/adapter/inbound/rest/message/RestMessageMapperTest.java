package org.bazar.chat.adapter.inbound.rest.message;

import org.bazar.chat.AbstractTest;
import org.bazar.chat.adapter.inbound.rest.message.dto.V1CreateMessageRequest;
import org.bazar.chat.adapter.inbound.rest.message.dto.V1GetMessageResponse;
import org.bazar.chat.app.api.message.dto.CreateMessageDto;
import org.bazar.chat.app.api.message.dto.GetMessageDto;
import org.bazar.chat.app.api.message.dto.UpdateMessageDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.bazar.chat.TestDataTransformUtil.readObjectFromFile;

public class RestMessageMapperTest extends AbstractTest {
    private final RestMessageMapper restMessageMapper = Mappers.getMapper(RestMessageMapper.class);

    @Test
    void toV1GetMessageResponse() {
        GetMessageDto getMessageDto =
                readObjectFromFile("/RestMessageMapperTest/GetMessageDto.json", GetMessageDto.class);

        V1GetMessageResponse result = restMessageMapper.toV1GetMessageResponse(getMessageDto);

        assertEqualsToFile(result, "/RestMessageMapperTest/V1GetMessageResponse_expected.json");
    }

    @Test
    void toCreateMessageDto() {
        V1CreateMessageRequest v1CreateMessageRequest = readObjectFromFile(
                "/RestMessageMapperTest/V1CreateMessageRequest.json",
                V1CreateMessageRequest.class
        );

        CreateMessageDto result = restMessageMapper.toCreateMessageDto(5L, v1CreateMessageRequest);

        assertEqualsToFile(result, "/RestMessageMapperTest/CreateMessageDto_expected.json");
    }

    @Test
    void toUpdateMessageDto() {
        UpdateMessageDto result = restMessageMapper.toUpdateMessageDto(5L, 10L, "newContent");

        assertEqualsToFile(result, "/RestMessageMapperTest/UpdateMessageDto_expected.json");
    }
}
