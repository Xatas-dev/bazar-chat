package org.bazar.chat.it.controller;

import builder.CreateChatRequestBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.model.ChatResponse;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static builder.CreateChatRequestBuilder.DEFAULT_SPACE_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChatControllerIntegrationTest extends AbstractControllerIntegrationTest {
    private static final Long SPACE_ID = 10L;
    private static final TypeReference<ChatResponse> TYPE_REF_CHAT_DTO = new TypeReference<>() {};

    @Test
    void getChatBySpace_success() throws Exception {
        testDataHelper.createChatWith(SPACE_ID);

        ChatResponse chat = restTestUtil.getPerform(
                GET_CHAT_BY_SPACE_API_URL,
                Map.of("spaceId", SPACE_ID),
                TYPE_REF_CHAT_DTO,
                Map.of(),
                status().isOk()
        );

        assertNotNull(chat);
        assertEquals(SPACE_ID, chat.getSpaceId());
    }

    @Test
    void getChatBySpace_notFound() throws Exception {
        restTestUtil.getPerform(
                GET_CHAT_BY_SPACE_API_URL,
                Map.of("spaceId", SPACE_ID),
                TYPE_REFERENCE_STRING,
                Map.of(),
                status().isNotFound()
        );
    }

    @Test
    void createChat_success() throws Exception {
        ChatResponse chatResponse = restTestUtil.postPerform(
                CREATE_CHAT_API_URL,
                Map.of(),
                CreateChatRequestBuilder.buildDefault(),
                TYPE_REF_CHAT_DTO,
                Map.of(),
                status().isOk()
        );

        Chat chat = chatJpaRepository.findBySpaceId(DEFAULT_SPACE_ID).get();
        assertNotNull(chatResponse);
        assertNotNull(chat);
        assertEquals(DEFAULT_SPACE_ID, chatResponse.getSpaceId());
        assertEquals(DEFAULT_SPACE_ID, chat.getSpaceId());
    }
}
