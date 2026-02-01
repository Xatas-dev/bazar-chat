package builder;

import lombok.experimental.UtilityClass;
import org.bazar.chat.domain.chat.Chat;

@UtilityClass
public class ChatBuilder {
    public static final Long DEFAULT_SPACE_ID = 10L;

    public static Chat buildDefault() {
        Chat chat = new Chat();
        chat.setSpaceId(DEFAULT_SPACE_ID);
        return chat;
    }

    public static Chat buildWith(Long spaceId) {
        Chat chat = new Chat();
        chat.setSpaceId(spaceId);
        return chat;
    }
}
