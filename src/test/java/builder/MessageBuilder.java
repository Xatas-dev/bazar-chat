package builder;

import lombok.experimental.UtilityClass;
import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.domain.message.Message;

import java.util.UUID;

@UtilityClass
public class MessageBuilder {

    public static Message buildWith(Chat chat, String content, UUID userId, boolean visible) {
        Message message = new Message();
        message.setUserId(userId);
        message.setChat(chat);
        message.setContent(content);
        message.setVisible(visible);
        return message;
    }

    public static Message buildWith(Chat chat, String content, UUID userId) {
        Message message = new Message();
        message.setUserId(userId);
        message.setChat(chat);
        message.setContent(content);
        return message;
    }
}
