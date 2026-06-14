package builder;

import lombok.experimental.UtilityClass;
import org.bazar.chat.adapter.inbound.rest.message.dto.V1CreateMessageRequest;

@UtilityClass
public class V1CreateMessageRequestBuilder {
    public static final String DEFAULT_CONTENT = "content";

    public static V1CreateMessageRequest buildDefault() {
        return new V1CreateMessageRequest(DEFAULT_CONTENT, null);
    }

    public static V1CreateMessageRequest buildWith(Long replyMessageId) {
        return new V1CreateMessageRequest(DEFAULT_CONTENT, replyMessageId);
    }
}
