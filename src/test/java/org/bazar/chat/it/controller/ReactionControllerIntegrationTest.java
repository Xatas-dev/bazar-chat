package org.bazar.chat.it.controller;

import builder.ChatBuilder;
import builder.JwtBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import org.bazar.chat.app.api.exception.ErrorCode;
import org.bazar.chat.app.api.reaction.dto.UserStatus;
import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.domain.message.Message;
import org.bazar.chat.domain.reaction.MessageReaction;
import org.bazar.chat.domain.reaction.Reaction;
import org.bazar.chat.model.MessageReactionEntry;
import org.bazar.chat.model.MessageReactionListResponse;
import org.bazar.chat.model.ReactionUpdateResponse;
import org.bazar.chat.model.ReactionUserEntry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

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
    private static final TypeReference<MessageReactionListResponse> TYPE_REF_REACTIONS_LIST_DTO = new TypeReference<>() {};
    private static final TypeReference<ReactionUpdateResponse> TYPE_REFERENCE_REACTION_UPDATE_DTO = new TypeReference<>() {};

    @Test
    @DisplayName("Успешное получение всех реакций и пользователей по сообщению")
    void getReactionsListByMessage_success() throws Exception {
        wireMockTestHelper.startMockBazarPersonaServer();
        wireMockTestHelper.stubBazarPersonaGetUsers_200(List.of(JwtBuilder.TEST_USER_ID), "/MessageControllerIntegrationTest/PersonaGetUsersResponse.json");
        Chat chat = testDataHelper.createChatWith(ChatBuilder.DEFAULT_SPACE_ID);
        Message message = testDataHelper.createMessageWith(chat, TEST_CONTENT, JwtBuilder.TEST_USER_ID, true);
        Reaction reaction = reactionJpaRepository.findById(REACTION_1ID).get();
        testDataHelper.createMessageReactionWith(message, reaction, JwtBuilder.TEST_USER_ID);

        MessageReactionListResponse response = restTestUtil.getPerform(
                String.format(GET_REACTIONS_BY_MESSAGE_ID, chat.getId(), message.getId()),
                Map.of(),
                TYPE_REF_REACTIONS_LIST_DTO,
                Map.of(),
                status().isOk()
        );

        assertNotNull(response);
        assertNotNull(response.getReactions());
        assertEquals(1, response.getReactions().size());
        MessageReactionEntry reactionEntry = response.getReactions().getFirst();
        assertEquals(REACTION_1ID, reactionEntry.getReactionId());
        assertEquals(1, reactionEntry.getUsers().size());
        ReactionUserEntry user = reactionEntry.getUsers().getFirst();
        assertEquals(JwtBuilder.TEST_USER_ID, user.getUserId());
        assertEquals("Jane", user.getFirstName());
        assertEquals(UserStatus.EXIST.name(), user.getStatus());
    }

    @Test
    @DisplayName("Получение реакций по сообщению когда пользователь не найден в bazar-persona")
    void getReactionsListByMessage_userNotFound() throws Exception {
        wireMockTestHelper.startMockBazarPersonaServer();
        wireMockTestHelper.stubBazarPersonaGetUsers_notFound(List.of(JwtBuilder.TEST_USER_ID));
        Chat chat = testDataHelper.createChatWith(ChatBuilder.DEFAULT_SPACE_ID);
        Message message = testDataHelper.createMessageWith(chat, TEST_CONTENT, JwtBuilder.TEST_USER_ID, true);
        Reaction reaction = reactionJpaRepository.findById(REACTION_1ID).get();
        testDataHelper.createMessageReactionWith(message, reaction, JwtBuilder.TEST_USER_ID);

        MessageReactionListResponse response = restTestUtil.getPerform(
                String.format(GET_REACTIONS_BY_MESSAGE_ID, chat.getId(), message.getId()),
                Map.of(),
                TYPE_REF_REACTIONS_LIST_DTO,
                Map.of(),
                status().isOk()
        );

        assertNotNull(response);
        assertNotNull(response.getReactions());
        assertEquals(1, response.getReactions().size());
        MessageReactionEntry reactionEntry = response.getReactions().getFirst();
        assertEquals(REACTION_1ID, reactionEntry.getReactionId());
        assertEquals(1, reactionEntry.getUsers().size());
        ReactionUserEntry user = reactionEntry.getUsers().getFirst();
        assertEquals(JwtBuilder.TEST_USER_ID, user.getUserId());
        assertEquals(UserStatus.UNKNOWN.name(), user.getStatus());
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
    }

    @Test
    @DisplayName("Успешное добавление реакции")
    void addMessageReaction_success() throws Exception {
        wireMockTestHelper.startMockBazarPersonaServer();
        wireMockTestHelper.stubBazarPersonaGetUsers_200(List.of(JwtBuilder.TEST_USER_ID), "/MessageControllerIntegrationTest/PersonaGetUsersResponse.json");
        Chat chat = testDataHelper.createChatWith(ChatBuilder.DEFAULT_SPACE_ID);
        Message message = testDataHelper.createMessageWith(chat, TEST_CONTENT, JwtBuilder.TEST_USER_ID, true);

        ReactionUpdateResponse reactionUpdateResponse = restTestUtil.putPerform(
                String.format(UPDATE_REACTION_API_URL, chat.getId(), message.getId(), REACTION_1ID),
                Map.of(),
                null,
                TYPE_REFERENCE_REACTION_UPDATE_DTO,
                Map.of(),
                status().isOk()
        );

        assertNotNull(reactionUpdateResponse);
        assertEquals(REACTION_1ID, Long.valueOf(reactionUpdateResponse.getReactionId()));
        assertEquals(message.getId(), Long.valueOf(reactionUpdateResponse.getMessageId()));
        assertEquals(1L, reactionUpdateResponse.getCount());
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
        Chat chat = testDataHelper.createChatWith(ChatBuilder.DEFAULT_SPACE_ID);
        Message message = testDataHelper.createMessageWith(chat, TEST_CONTENT, JwtBuilder.TEST_USER_ID, true);
        Reaction reaction = reactionJpaRepository.findById(REACTION_1ID).get();
        testDataHelper.createMessageReactionWith(message, reaction, JwtBuilder.TEST_USER_ID);

        ReactionUpdateResponse reactionUpdateResponse = restTestUtil.putPerform(
                String.format(UPDATE_REACTION_API_URL, chat.getId(), message.getId(), REACTION_1ID),
                Map.of(),
                null,
                TYPE_REFERENCE_REACTION_UPDATE_DTO,
                Map.of(),
                status().isOk()
        );

        assertNotNull(reactionUpdateResponse);
        assertEquals(REACTION_1ID, Long.valueOf(reactionUpdateResponse.getReactionId()));
        assertEquals(message.getId(), Long.valueOf(reactionUpdateResponse.getMessageId()));
        assertEquals(0L, reactionUpdateResponse.getCount());
        List<MessageReaction> messageReactions = messageReactionJpaRepository.findAll();
        assertTrue(messageReactions.isEmpty());
    }

    @Test
    @DisplayName("Неуспешное добавление реакции, слишком много реакций у пользователя")
    void addMessageReaction_failure_maxReactionsPerUser() throws Exception {
        Chat chat = testDataHelper.createChatWith(ChatBuilder.DEFAULT_SPACE_ID);
        Message message = testDataHelper.createMessageWith(chat, TEST_CONTENT, JwtBuilder.TEST_USER_ID, true);
        Reaction reaction1 = reactionJpaRepository.findById(REACTION_1ID).get();
        Reaction reaction2 = reactionJpaRepository.findById(REACTION_2ID).get();
        Reaction reaction3 = reactionJpaRepository.findById(REACTION_3ID).get();
        testDataHelper.createMessageReactionWith(message, reaction1, JwtBuilder.TEST_USER_ID);
        testDataHelper.createMessageReactionWith(message, reaction2, JwtBuilder.TEST_USER_ID);
        testDataHelper.createMessageReactionWith(message, reaction3, JwtBuilder.TEST_USER_ID);

        String errorText = restTestUtil.putPerform(
                String.format(UPDATE_REACTION_API_URL, chat.getId(), message.getId(), REACTION_4ID),
                Map.of(),
                null,
                TYPE_REFERENCE_STRING,
                Map.of(),
                status().isBadRequest()
        );

        assertEquals(String.format(ErrorCode.MAX_REACTIONS_PER_USER_ON_MESSAGE.formatMessage(JwtBuilder.TEST_USER_ID, message.getId())),
                errorText);
    }
}
