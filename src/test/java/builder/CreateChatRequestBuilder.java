package builder;

import lombok.experimental.UtilityClass;
import org.bazar.chat.model.CreateChatRequest;

@UtilityClass
public class CreateChatRequestBuilder {
    public static final Long DEFAULT_SPACE_ID = 1L;

    public static CreateChatRequest buildDefault() {
        return new CreateChatRequest(DEFAULT_SPACE_ID);
    }
}
