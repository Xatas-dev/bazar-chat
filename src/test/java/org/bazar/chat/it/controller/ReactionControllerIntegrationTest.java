package org.bazar.chat.it.controller;

import builder.JwtBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import org.bazar.chat.adapter.inbound.rest.reaction.dto.V1MessageReactionListResponse;
import org.bazar.chat.adapter.inbound.rest.reaction.dto.V1ReactionUpdateResponse;
import org.bazar.chat.app.api.reaction.dto.UserStatus;
import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.domain.message.Message;
import org.bazar.chat.domain.reaction.MessageReaction;
import org.bazar.chat.domain.reaction.Reaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static builder.ChatBuilder.DEFAULT_SPACE_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReactionControllerIntegrationTest extends AbstractControllerIntegrationTest {
    private static final String TEST_CONTENT = "test";
    private static final Long REACTION_1ID = 1L;
    private static final Long REACTION_2ID = 2L;
    private static final Long REACTION_3ID = 3L;
    private static final Long REACTION_4ID = 4L;
    private static final TypeReference<V1MessageReactionListResponse> TYPE_REF_V1_MESSAGE_REACTION_LIST_RESPONSE = new TypeReference<>() {};
    private static final TypeReference<V1ReactionUpdateResponse> TYPE_REF_V1_REACTION_UPDATE_RESPONSE = new TypeReference<>() {};

    @Test
    @DisplayName("Успешное получение всех реакций и пользователей по сообщению")
    void getReactionsListByMessage_success() throws Exception {
        wireMockTestHelper.startMockBazarPersonaServer();
        wireMockTestHelper.stubBazarPersonaGetUsers_200(List.of(JwtBuilder.TEST_USER_ID), "/MessageControllerIntegrationTest/PersonaGetUsersResponse.json");
        Chat chat = testDataHelper.createChatWith(DEFAULT_SPACE_ID);
        Message message = testDataHelper.createMessageWith(chat, TEST_CONTENT, JwtBuilder.TEST_USER_ID, true);
        Reaction reaction = reactionJpaRepository.findById(REACTION_1ID).get();
        testDataHelper.createMessageReactionWith(message, reaction, JwtBuilder.TEST_USER_ID);

        V1MessageReactionListResponse response = restTestUtil.getPerform(
                String.format(GET_REACTIONS_BY_MESSAGE_ID, DEFAULT_SPACE_ID, chat.getId(), message.getId()),
                Map.of(),
                TYPE_REF_V1_MESSAGE_REACTION_LIST_RESPONSE,
                Map.of(),
                status().isOk()
        );

        assertNotNull(response);
        assertNotNull(response.reactions());
        assertEquals(1, response.reactions().size());
        V1MessageReactionListResponse.V1MessageReactionEntry reactionEntry = response.reactions().getFirst();
        assertEquals(REACTION_1ID, reactionEntry.reactionId());
        assertEquals(1, reactionEntry.users().size());
        V1MessageReactionListResponse.V1ReactionUserEntry user = reactionEntry.users().getFirst();
        assertEquals(JwtBuilder.TEST_USER_ID, user.userId());
        assertEquals("Jane", user.firstName());
        assertEquals(UserStatus.EXIST.name(), user.status());
    }

    @Test
    @DisplayName("Получение реакций по сообщению когда пользователь не найден в bazar-persona")
    void getReactionsListByMessage_userNotFound() throws Exception {
        wireMockTestHelper.startMockBazarPersonaServer();
        wireMockTestHelper.stubBazarPersonaGetUsers_notFound(List.of(JwtBuilder.TEST_USER_ID));
        Chat chat = testDataHelper.createChatWith(DEFAULT_SPACE_ID);
        Message message = testDataHelper.createMessageWith(chat, TEST_CONTENT, JwtBuilder.TEST_USER_ID, true);
        Reaction reaction = reactionJpaRepository.findById(REACTION_1ID).get();
        testDataHelper.createMessageReactionWith(message, reaction, JwtBuilder.TEST_USER_ID);

        V1MessageReactionListResponse response = restTestUtil.getPerform(
                String.format(GET_REACTIONS_BY_MESSAGE_ID, DEFAULT_SPACE_ID, chat.getId(), message.getId()),
                Map.of(),
                TYPE_REF_V1_MESSAGE_REACTION_LIST_RESPONSE,
                Map.of(),
                status().isOk()
        );

        assertNotNull(response);
        assertNotNull(response.reactions());
        assertEquals(1, response.reactions().size());
        V1MessageReactionListResponse.V1MessageReactionEntry reactionEntry = response.reactions().getFirst();
        assertEquals(REACTION_1ID, reactionEntry.reactionId());
        assertEquals(1, reactionEntry.users().size());
        V1MessageReactionListResponse.V1ReactionUserEntry user = reactionEntry.users().getFirst();
        assertEquals(JwtBuilder.TEST_USER_ID, user.userId());
        assertEquals(UserStatus.UNKNOWN.name(), user.status());
        assertNull(user.firstName());
        assertNull(user.lastName());
    }

    @Test
    @DisplayName("Успешное добавление реакции")
    void addMessageReaction_success() throws Exception {
        wireMockTestHelper.startMockBazarPersonaServer();
        wireMockTestHelper.stubBazarPersonaGetUsers_200(List.of(JwtBuilder.TEST_USER_ID), "/MessageControllerIntegrationTest/PersonaGetUsersResponse.json");
        Chat chat = testDataHelper.createChatWith(DEFAULT_SPACE_ID);
        Message message = testDataHelper.createMessageWith(chat, TEST_CONTENT, JwtBuilder.TEST_USER_ID, true);

        V1ReactionUpdateResponse reactionUpdateResponse = restTestUtil.putPerform(
                String.format(UPDATE_REACTION_API_URL, DEFAULT_SPACE_ID, chat.getId(), message.getId(), REACTION_1ID),
                Map.of(),
                null,
                TYPE_REF_V1_REACTION_UPDATE_RESPONSE,
                Map.of(),
                status().isOk()
        );

        assertNotNull(reactionUpdateResponse);
        assertEquals(REACTION_1ID, Long.valueOf(reactionUpdateResponse.reactions().getFirst().reactionId()));
        assertEquals(message.getId(), Long.valueOf(reactionUpdateResponse.messageId()));
        assertEquals(1L, reactionUpdateResponse.reactions().getFirst().count());
        assertEquals(REACTION_1ID, Long.valueOf(reactionUpdateResponse.reactions().getFirst().reactionId()));
        assertEquals(message.getId(), Long.valueOf(reactionUpdateResponse.messageId()));
        assertEquals(1L, reactionUpdateResponse.reactions().getFirst().count());
        MessageReaction messageReaction = messageReactionJpaRepository.findAll().getFirst();
        assertEquals(message.getId(), messageReaction.getMessage().getId());
        assertEquals(REACTION_1ID, messageReaction.getReaction().getId());
        assertEquals(JwtBuilder.TEST_USER_ID, messageReaction.getUserId());
    }

    @Test
    @DisplayName("Успешное удаление реакции")
    void removeMessageReaction_success() throws Exception {
        wireMockTestHelper.startMockBazarPersonaServer();
        wireMockTestHelper.stubBazarPersonaGetUsers_200(List.of(JwtBuilder.TEST_USER_ID), "/MessageControllerIntegrationTest/PersonaGetUsersResponse.json");
        Chat chat = testDataHelper.createChatWith(DEFAULT_SPACE_ID);
        Message message = testDataHelper.createMessageWith(chat, TEST_CONTENT, JwtBuilder.TEST_USER_ID, true);
        Reaction reaction = reactionJpaRepository.findById(REACTION_1ID).get();
        testDataHelper.createMessageReactionWith(message, reaction, JwtBuilder.TEST_USER_ID);

        V1ReactionUpdateResponse reactionUpdateResponse = restTestUtil.putPerform(
                String.format(UPDATE_REACTION_API_URL, DEFAULT_SPACE_ID, chat.getId(), message.getId(), REACTION_1ID),
                Map.of(),
                null,
                TYPE_REF_V1_REACTION_UPDATE_RESPONSE,
                Map.of(),
                status().isOk()
        );

        assertNotNull(reactionUpdateResponse);
        assertEquals(REACTION_1ID, Long.valueOf(reactionUpdateResponse.reactions().getFirst().reactionId()));
        assertEquals(message.getId(), Long.valueOf(reactionUpdateResponse.messageId()));
        assertEquals(0L, reactionUpdateResponse.reactions().getFirst().count());
        List<MessageReaction> messageReactions = messageReactionJpaRepository.findAll();
        assertTrue(messageReactions.isEmpty());
    }

    @Test
    @DisplayName("Удаление самой старой реакции при добавлении четвертой")
    void addMessageReaction_failure_maxReactionsPerUser() throws Exception {
        Chat chat = testDataHelper.createChatWith(DEFAULT_SPACE_ID);
        Message message = testDataHelper.createMessageWith(chat, TEST_CONTENT, JwtBuilder.TEST_USER_ID, true);
        Reaction reaction1 = reactionJpaRepository.findById(REACTION_1ID).get();
        Reaction reaction2 = reactionJpaRepository.findById(REACTION_2ID).get();
        Reaction reaction3 = reactionJpaRepository.findById(REACTION_3ID).get();
        testDataHelper.createMessageReactionWith(message, reaction1, JwtBuilder.TEST_USER_ID);
        testDataHelper.createMessageReactionWith(message, reaction2, JwtBuilder.TEST_USER_ID);
        testDataHelper.createMessageReactionWith(message, reaction3, JwtBuilder.TEST_USER_ID);

        V1ReactionUpdateResponse reactionUpdateResponse = restTestUtil.putPerform(
                String.format(UPDATE_REACTION_API_URL, DEFAULT_SPACE_ID, chat.getId(), message.getId(), REACTION_4ID),
                Map.of(),
                null,
                TYPE_REF_V1_REACTION_UPDATE_RESPONSE,
                Map.of(),
                status().isOk()
        );

        assertNotNull(reactionUpdateResponse);
        assertEquals(message.getId(), Long.valueOf(reactionUpdateResponse.messageId()));
        assertEquals(REACTION_4ID, Long.valueOf(reactionUpdateResponse.reactions().get(1).reactionId()));
        assertEquals(0L, reactionUpdateResponse.reactions().get(0).count());
        assertEquals(1L, reactionUpdateResponse.reactions().get(1).count());
        List<MessageReaction> messageReactions = messageReactionJpaRepository.findAll();
        assertEquals(3, messageReactions.size());
    }
}
