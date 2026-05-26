package builder;

import lombok.experimental.UtilityClass;
import org.bazar.chat.domain.message.Message;
import org.bazar.chat.domain.reaction.MessageReaction;
import org.bazar.chat.domain.reaction.Reaction;

import java.util.UUID;

@UtilityClass
public class MessageReactionBuilder {
    public static MessageReaction buildWith(Message message, Reaction reaction, UUID userId) {
        MessageReaction messageReaction = new MessageReaction();
        messageReaction.setMessage(message);
        messageReaction.setReaction(reaction);
        messageReaction.setUserId(userId);
        return messageReaction;
    }
}
