package org.bazar.chat.it.testutil;

import builder.ChatBuilder;
import builder.MessageBuilder;
import builder.MessageReactionBuilder;
import builder.ReactionBuilder;
import lombok.RequiredArgsConstructor;
import org.bazar.chat.adapter.outbound.persistence.chat.ChatJpaRepository;
import org.bazar.chat.adapter.outbound.persistence.message.MessageJpaRepository;
import org.bazar.chat.adapter.outbound.persistence.reaction.MessageReactionJpaRepository;
import org.bazar.chat.adapter.outbound.persistence.reaction.ReactionJpaRepository;
import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.domain.message.Message;
import org.bazar.chat.domain.reaction.MessageReaction;
import org.bazar.chat.domain.reaction.Reaction;
import org.bazar.chat.domain.reaction.ReactionType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TestDataHelper {
    private final ChatJpaRepository chatJpaRepository;
    private final MessageJpaRepository messageJpaRepository;
    private final ReactionJpaRepository reactionJpaRepository;
    private final MessageReactionJpaRepository messageReactionJpaRepository;

    public Chat createChatWith(Long spaceId) {
        return chatJpaRepository.save(ChatBuilder.buildWith(spaceId));
    }

    public Message createMessageWith(Chat chat, String content, UUID userId) {
        return messageJpaRepository.save(MessageBuilder.buildWith(chat, content, userId));
    }

    public Message createMessageWith(Chat chat, String content, UUID userId, boolean visible) {
        return messageJpaRepository.save(MessageBuilder.buildWith(chat, content, userId, visible));
    }

    public Message createMessageWith(Chat chat, String content, UUID userId, boolean visible, Message reply) {
        return messageJpaRepository.save(MessageBuilder.buildWith(chat, content, userId, visible, reply));
    }

    public Reaction createReactionDefault() {
        return reactionJpaRepository.save(ReactionBuilder.buildDefault());
    }

    public Reaction createReactionWith(String code, String value, String type) {
        return reactionJpaRepository.save(ReactionBuilder.buildWith(code, value, ReactionType.valueOf(type)));
    }

    public MessageReaction createMessageReactionWith(Message message, Reaction reaction) {
        return messageReactionJpaRepository.save(MessageReactionBuilder.buildWith(message, reaction));
    }

    public MessageReaction createMessageReactionWith(Message message, Reaction reaction, UUID userId) {
        return messageReactionJpaRepository.save(MessageReactionBuilder.buildWith(message, reaction));
    }

    public MessageReaction createMessageReactionWith(Message message, Long reactionId, UUID userId) {
        Reaction reaction = reactionJpaRepository.findById(reactionId).orElse(null);
        return messageReactionJpaRepository.save(MessageReactionBuilder.buildWith(message, reaction, userId));
    }

    public void clearTables() {
        messageReactionJpaRepository.deleteAll();
        messageJpaRepository.deleteAll();
        chatJpaRepository.deleteAll();
    }
}
