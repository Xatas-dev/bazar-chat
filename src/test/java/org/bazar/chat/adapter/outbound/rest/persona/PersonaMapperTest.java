package org.bazar.chat.adapter.outbound.rest.persona;

import org.bazar.chat.AbstractTest;
import org.bazar.chat.adapter.outbound.rest.persona.dto.PersonaUserResponse;
import org.bazar.chat.app.api.persona.model.UserDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.bazar.chat.TestDataTransformUtil.readObjectFromFile;

public class PersonaMapperTest extends AbstractTest {
    private final PersonaMapper personaMapper = Mappers.getMapper(PersonaMapper.class);

    @Test
    void mapToUserDto() {
        PersonaUserResponse personaUserResponse =
                readObjectFromFile("/PersonaMapperTest/PersonaUserResponse.json", PersonaUserResponse.class);

        UserDto result = personaMapper.mapToUserDto(personaUserResponse);

        assertEqualsToFile(result, "/PersonaMapperTest/UserDto_expected.json");
    }
}
