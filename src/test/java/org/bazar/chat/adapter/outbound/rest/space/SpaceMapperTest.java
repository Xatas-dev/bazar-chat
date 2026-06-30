package org.bazar.chat.adapter.outbound.rest.space;

import com.fasterxml.jackson.core.type.TypeReference;
import org.bazar.chat.AbstractTest;
import org.bazar.chat.adapter.outbound.rest.space.dto.SpaceUserResponse;
import org.bazar.chat.app.api.space.dto.SpaceUserDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Set;

import static org.bazar.chat.TestDataTransformUtil.readObjectFromFile;

public class SpaceMapperTest extends AbstractTest {
    private final SpaceMapper spaceMapper = Mappers.getMapper(SpaceMapper.class);

    @Test
    void toSpaceUserDtoSet() {
        Set<SpaceUserResponse.UserInSpaceDto> spaceUsers =
                readObjectFromFile("/SpaceMapperTest/UserInSpaceDtoSet.json", new TypeReference<>() {});

        Set<SpaceUserDto> result = spaceMapper.toSpaceUserDtoSet(spaceUsers);

        assertEqualsToFile(result, "/SpaceMapperTest/SpaceUserDtoSet_expected.json");
    }
}
