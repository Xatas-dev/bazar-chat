package builder;

import lombok.experimental.UtilityClass;
import org.bazar.chat.adapter.inbound.rest.message.dto.V1DeleteMessageRequest;

import java.util.List;

@UtilityClass
public class V1DeleteMessageRequestBuilder {
    public V1DeleteMessageRequest buildWith(List<Long> messageIds) {
        return new V1DeleteMessageRequest(messageIds);
    }
}
