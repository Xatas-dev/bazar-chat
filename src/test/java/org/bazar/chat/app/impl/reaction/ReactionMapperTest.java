package org.bazar.chat.app.impl.reaction;

import org.bazar.chat.AbstractTest;
import org.bazar.chat.app.api.persona.model.UserDto;
import org.bazar.chat.app.api.reaction.dto.GetReactionDto;
import org.bazar.chat.app.api.reaction.dto.ReactionUserDto;
import org.bazar.chat.domain.reaction.Reaction;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.bazar.chat.TestDataTransformUtil.readObjectFromFile;
import static org.bazar.chat.app.api.reaction.dto.UserStatus.UNKNOWN;

public class ReactionMapperTest extends AbstractTest {
    private final ReactionMapper reactionMapper = Mappers.getMapper(ReactionMapper.class);

    @Test
    void toGetReactionDto() {
        Reaction reaction = readObjectFromFile("/ReactionMapperTest/Reaction.json", Reaction.class);

        GetReactionDto result = reactionMapper.toGetReactionDto(reaction);

        assertEqualsToFile(result, "/ReactionMapperTest/GetReactionDto_expected.json");
    }

    @Test
    void toReactionUserDto() {
        UserDto userDto = readObjectFromFile("/ReactionMapperTest/UserDto.json", UserDto.class);

        ReactionUserDto result = reactionMapper.toReactionUserDto(userDto, UNKNOWN);

        assertEqualsToFile(result, "/ReactionMapperTest/ReactionUserDto_expected.json");
    }
}
