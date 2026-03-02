package builder;

import lombok.experimental.UtilityClass;
import org.bazar.chat.model.UpdateChatMessageRequest;

@UtilityClass
public class UpdateChatMessageRequestBuilder {
    public static UpdateChatMessageRequest buildWith(String newContent) {
        return new UpdateChatMessageRequest(newContent);
    }
}
