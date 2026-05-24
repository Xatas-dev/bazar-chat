package org.bazar.chat.it.controller;

import builder.ChatBuilder;
import builder.JwtBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import org.bazar.chat.app.api.reaction.dto.UserStatus;
import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.domain.message.Message;
import org.bazar.chat.domain.reaction.Reaction;
import org.bazar.chat.model.MessageReactionEntry;
import org.bazar.chat.model.MessageReactionListResponse;
import org.bazar.chat.model.ReactionUserEntry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReactionControllerIntegrationTest extends AbstractControllerIntegrationTest {
    private static final String TEST_CONTENT = "test";
    private static final TypeReference<MessageReactionListResponse> TYPE_REF_REACTIONS_LIST_DTO = new TypeReference<>() {};

    @Test
    @DisplayName("Успешное получение всех реакций + пользователей по сообщению")
    void getReactionsListByMessage_success() throws Exception {
        wireMockTestHelper.startMockBazarPersonaServer();
        wireMockTestHelper.stubBazarPersonaGetUsers_200(List.of(JwtBuilder.TEST_USER_ID), "/MessageControllerIntegrationTest/PersonaGetUsersResponse.json");

        Chat chat = testDataHelper.createChatWith(ChatBuilder.DEFAULT_SPACE_ID);
        Message message = testDataHelper.createMessageWith(chat, TEST_CONTENT, JwtBuilder.TEST_USER_ID, true);
        Reaction reaction = testDataHelper.createReactionWith("ок", "👍", "UNICODE");

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
        assertEquals(reaction.getId(), reactionEntry.getReactionId());
        assertEquals(1, reactionEntry.getUsers().size());

        ReactionUserEntry user = reactionEntry.getUsers().getFirst();
        assertEquals(JwtBuilder.TEST_USER_ID, user.getUserId());
        assertEquals("Jane", user.getFirstName());
        assertEquals(UserStatus.EXIST.name(), user.getStatus());

        testDataHelper.clearTables();
    }

    @Test
    @DisplayName("Получение реакций по сообщению когда пользователь не найден в bazar-persona")
    void getReactionsListByMessage_userNotFound() throws Exception {
        wireMockTestHelper.startMockBazarPersonaServer();
        wireMockTestHelper.stubBazarPersonaGetUsers_404(List.of(JwtBuilder.TEST_USER_ID));

        Chat chat = testDataHelper.createChatWith(ChatBuilder.DEFAULT_SPACE_ID);
        Message message = testDataHelper.createMessageWith(chat, TEST_CONTENT, JwtBuilder.TEST_USER_ID, true);
        Reaction reaction = testDataHelper.createReactionWith("likes", "👍", "UNICODE");

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
        assertEquals(reaction.getId(), reactionEntry.getReactionId());
        assertEquals(1, reactionEntry.getUsers().size());

        ReactionUserEntry user = reactionEntry.getUsers().getFirst();
        assertEquals(JwtBuilder.TEST_USER_ID, user.getUserId());
        assertEquals(UserStatus.UNKNOWN.name(), user.getStatus());
        assertNull(user.getFirstName());
        assertNull(user.getLastName());

        testDataHelper.clearTables();
    }
}
