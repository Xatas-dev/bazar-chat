package org.bazar.chat.adapter.inbound.rest.reaction;

import org.bazar.chat.AbstractTest;
import org.bazar.chat.adapter.inbound.rest.reaction.dto.V1MessageReactionListResponse;
import org.bazar.chat.adapter.inbound.rest.reaction.dto.V1ReactionUpdateResponse;
import org.bazar.chat.app.api.reaction.dto.MessageReactionListDto;
import org.bazar.chat.app.api.reaction.dto.UpdatedReactionsDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.bazar.chat.TestDataTransformUtil.readObjectFromFile;

public class RestReactionMapperTest extends AbstractTest {
    private final RestReactionMapper restReactionMapper = Mappers.getMapper(RestReactionMapper.class);

    @Test
    void toV1MessageReactionListResponse() {
        MessageReactionListDto messageReactionListDto = readObjectFromFile(
                "/RestReactionMapperTest/MessageReactionListDto.json",
                MessageReactionListDto.class
        );

        V1MessageReactionListResponse result =
                restReactionMapper.toV1MessageReactionListResponse(messageReactionListDto);

        assertEqualsToFile(result, "/RestReactionMapperTest/V1MessageReactionListResponse_expected.json");
    }

    @Test
    void toV1ReactionUpdateResponse() {
        UpdatedReactionsDto updatedReactionsDto =
                readObjectFromFile("/RestReactionMapperTest/UpdatedReactionsDto.json",UpdatedReactionsDto.class);

        V1ReactionUpdateResponse result = restReactionMapper.toV1ReactionUpdateResponse(updatedReactionsDto);

        assertEqualsToFile(result, "/RestReactionMapperTest/V1ReactionUpdateResponse_expected.json");
    }
}
