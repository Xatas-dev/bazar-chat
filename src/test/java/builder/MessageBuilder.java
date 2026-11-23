package builder;

import lombok.experimental.UtilityClass;
import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.domain.message.Message;

import java.util.UUID;

@UtilityClass
public class MessageBuilder {
    public static Message buildWith(Chat chat) {
        Message message = new Message();
        message.setChat(chat);
        message.setVisible(true);
        message.setUserId(JwtBuilder.TEST_USER_ID);
        return message;
    }

    public static Message buildWith(Chat chat, UUID userId, boolean visible) {
        Message message = new Message();
        message.setUserId(userId);
        message.setChat(chat);
        message.setVisible(visible);
        return message;
    }

    public static Message buildWith(Chat chat, UUID userId) {
        Message message = new Message();
        message.setUserId(userId);
        message.setChat(chat);
        return message;
    }
}
