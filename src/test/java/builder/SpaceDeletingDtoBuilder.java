package builder;

import lombok.experimental.UtilityClass;
import org.bazar.chat.adapter.inbound.kafka.dto.SpaceDeletingDto;

@UtilityClass
public class SpaceDeletingDtoBuilder {
    public static final Long SPACE_ID = 10L;

    public static SpaceDeletingDto buildDefault() {
        return new SpaceDeletingDto(SPACE_ID);
    }
}
