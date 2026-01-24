package builder;

import lombok.experimental.UtilityClass;
import org.bazar.chat.model.CreateMessageRequest;

@UtilityClass
public class CreateMessageRequestBuilder {
    public static final String DEFAULT_CONTENT = "content";

    public static CreateMessageRequest buildDefault() {
        return new CreateMessageRequest(DEFAULT_CONTENT);
    }
}
