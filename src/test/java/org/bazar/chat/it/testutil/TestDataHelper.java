package org.bazar.chat.it.testutil;

import builder.ChatBuilder;
import builder.MessageBuilder;
import builder.MessageReactionBuilder;
import builder.ReactionBuilder;
import builder.V1CreateMessageRequestBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bazar.chat.adapter.inbound.rest.chat.dto.V1CreateChatResponse;
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

import java.util.Comparator;
import java.util.Map;
import java.util.UUID;

import static builder.ChatBuilder.DEFAULT_SPACE_ID;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
@RequiredArgsConstructor
public class TestDataHelper {
    private static final String CREATE_CHAT_API_URL = "/api/v1/spaces/%s/chats";
    protected static final String CREATE_MESSAGE_API_URL = "/api/v1/spaces/%s/chats/%s/messages";
    private static final TypeReference<V1CreateChatResponse> TYPE_V1_CREATE_CHAT_RESPONSE = new TypeReference<>() {};
    protected static final TypeReference<Void> TYPE_REFERENCE_VOID = new TypeReference<>() {};
    private static final Long SPACE_ID = 10L;

    private final ChatJpaRepository chatJpaRepository;
    private final MessageJpaRepository messageJpaRepository;
    private final ReactionJpaRepository reactionJpaRepository;
    private final MessageReactionJpaRepository messageReactionJpaRepository;
    private final RestTestUtil restTestUtil;

    public Chat createChatWith(Long spaceId) {
        return chatJpaRepository.save(ChatBuilder.buildWith(spaceId));
    }

    public Message createMessageWith(Chat chat, String content, UUID userId) {
        return messageJpaRepository.save(MessageBuilder.buildWith(chat, content, userId));
    }

    public Message createMessageWith(Chat chat, String content, UUID userId, boolean visible) {
        return messageJpaRepository.save(MessageBuilder.buildWith(chat, content, userId, visible));
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
        return messageReactionJpaRepository.save(MessageReactionBuilder.buildWith(message, reaction, userId));
    }

    public V1CreateChatResponse createRealChat() throws Exception {
        return restTestUtil.postPerform(
                String.format(CREATE_CHAT_API_URL, SPACE_ID),
                Map.of(),
                null,
                TYPE_V1_CREATE_CHAT_RESPONSE,
                Map.of(),
                status().isOk()
        );
    }

    @Transactional
    public Message createRealMessage(Long chatId, String content, UUID userId, boolean visible) throws Exception {
        restTestUtil.postPerform(
                String.format(CREATE_MESSAGE_API_URL, DEFAULT_SPACE_ID, chatId),
                Map.of(),
                V1CreateMessageRequestBuilder.buildWith(content),
                TYPE_REFERENCE_VOID,
                Map.of(),
                status().isOk()
        );
        Message message =
                messageJpaRepository.findAll().stream().max(Comparator.comparing(Message::getCreatedAt)).orElseThrow();
        message.setUserId(userId);
        message.setVisible(visible);
        return message;
    }

    @Transactional
    public Message createRealMessage(Long chatId, String content, UUID userId, boolean visible, Long replyMessageId) throws Exception {
        restTestUtil.postPerform(
                String.format(CREATE_MESSAGE_API_URL, DEFAULT_SPACE_ID, chatId),
                Map.of(),
                V1CreateMessageRequestBuilder.buildWith(content, replyMessageId),
                TYPE_REFERENCE_VOID,
                Map.of(),
                status().isOk()
        );
        Message message =
                messageJpaRepository.findAll().stream().max(Comparator.comparing(Message::getCreatedAt)).orElseThrow();
        message.setUserId(userId);
        message.setVisible(visible);
        return message;
    }

    public Message createRealMessage(Long chatId) throws Exception {
        restTestUtil.postPerform(
                String.format(CREATE_MESSAGE_API_URL, DEFAULT_SPACE_ID, chatId),
                Map.of(),
                V1CreateMessageRequestBuilder.buildDefault(),
                TYPE_REFERENCE_VOID,
                Map.of(),
                status().isOk()
        );
        return messageJpaRepository.findAll().stream().max(Comparator.comparing(Message::getCreatedAt)).orElseThrow();
    }

    public void clearTables() {
        messageReactionJpaRepository.deleteAll();
        messageJpaRepository.deleteAll();
        chatJpaRepository.deleteAll();
    }
}
