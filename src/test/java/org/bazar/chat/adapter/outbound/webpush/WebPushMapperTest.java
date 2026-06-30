package org.bazar.chat.adapter.outbound.webpush;

import org.bazar.chat.AbstractTest;
import org.bazar.chat.app.api.message.dto.event.MessageCreatedEvent;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.bazar.chat.TestDataTransformUtil.readObjectFromFile;

public class WebPushMapperTest extends AbstractTest {
    private final WebPushMapper restReactionMapper = Mappers.getMapper(WebPushMapper.class);

    @Test
    void toPushPayloadDto() {
        MessageCreatedEvent messageCreatedEvent =
                readObjectFromFile("/WebPushMapperTest/MessageCreatedEvent.json", MessageCreatedEvent.class);

        PushPayloadDto result = restReactionMapper.toPushPayloadDto(messageCreatedEvent);

        assertEqualsToFile(result, "/WebPushMapperTest/PushPayloadDto_expected.json");
    }
}
