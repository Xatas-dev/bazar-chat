package builder;

import lombok.experimental.UtilityClass;
import org.bazar.chat.adapter.inbound.rest.message.dto.V1UpdateChatMessageRequest;

@UtilityClass
public class V1UpdateChatMessageRequestBuilder {
    public static V1UpdateChatMessageRequest buildWith(String newContent) {
        return new V1UpdateChatMessageRequest(newContent);
    }
}
