package builder;

import lombok.experimental.UtilityClass;
import org.bazar.chat.model.DeleteMessageRequest;

import java.util.List;

@UtilityClass
public class DeleteMessageRequestBuilder {
    public DeleteMessageRequest buildWith(List<Long> messageIds) {
        return new DeleteMessageRequest(messageIds);
    }
}
