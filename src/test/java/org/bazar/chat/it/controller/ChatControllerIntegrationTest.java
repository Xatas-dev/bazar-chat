package org.bazar.chat.it.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.bazar.chat.adapter.inbound.rest.chat.dto.V1CreateChatResponse;
import org.bazar.chat.adapter.inbound.rest.chat.dto.V1GetChatResponse;
import org.bazar.chat.adapter.inbound.rest.chat.dto.V1ReactionListResponse;
import org.bazar.chat.domain.chat.Chat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChatControllerIntegrationTest extends AbstractControllerIntegrationTest {
    private static final Long SPACE_ID = 10L;
    private static final TypeReference<V1CreateChatResponse> TYPE_V1_CREATE_CHAT_RESPONSE = new TypeReference<>() {};
    private static final TypeReference<V1GetChatResponse> TYPE_V1_GET_CHAT_RESPONSE = new TypeReference<>() {};
    private static final TypeReference<V1ReactionListResponse> TYPE_REF_REACTION_LIST_RESPONSE = new TypeReference<>() {};

    @Test
    @DisplayName("Успешное получение чата по идентификатору пространства")
    void getChatBySpace_success() throws Exception {
        testDataHelper.createChatWith(SPACE_ID);

        V1GetChatResponse chat = restTestUtil.getPerform(
                String.format(GET_CHAT_BY_SPACE_API_URL, SPACE_ID),
                Map.of(),
                TYPE_V1_GET_CHAT_RESPONSE,
                Map.of(),
                status().isOk()
        );

        assertNotNull(chat);
        assertEquals(SPACE_ID, chat.spaceId());
    }

    @Test
    @DisplayName("Неуспешное получение чата по идентификатору пространства - пространство не найдено")
    void getChatBySpace_notFound() throws Exception {
        restTestUtil.getPerform(
                String.format(GET_CHAT_BY_SPACE_API_URL, SPACE_ID),
                Map.of(),
                TYPE_REFERENCE_STRING,
                Map.of(),
                status().isNotFound()
        );
    }

    @Test
    @DisplayName("Успешное создание чата")
    void createChat_success() throws Exception {
        V1CreateChatResponse chatResponse = restTestUtil.postPerform(
                String.format(CREATE_CHAT_API_URL, SPACE_ID),
                Map.of(),
                null,
                TYPE_V1_CREATE_CHAT_RESPONSE,
                Map.of(),
                status().isOk()
        );

        Chat chat = chatJpaRepository.findBySpaceId(SPACE_ID).get();
        assertNotNull(chatResponse);
        assertNotNull(chat);
        assertEquals(SPACE_ID, chatResponse.spaceId());
        assertEquals(SPACE_ID, chat.getSpaceId());
    }

    @Test
    @DisplayName("Успешное получение реакций чата")
    void getChatReactions_success() throws Exception {
        Chat chat = testDataHelper.createChatWith(SPACE_ID);

        V1ReactionListResponse result = restTestUtil.getPerform(
                String.format(GET_CHAT_REACTIONS_API_URL, SPACE_ID, chat.getId()),
                Map.of(),
                TYPE_REF_REACTION_LIST_RESPONSE,
                Map.of(),
                status().isOk()
        );

        assertNotNull(result);
        assertFalse(result.reactions().isEmpty());
    }
}
