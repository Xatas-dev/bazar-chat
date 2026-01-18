package org.bazar.chat.it.controller;

import builder.ChatBuilder;
import builder.CreateMessageRequestBuilder;
import builder.JwtBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.domain.message.Message;
import org.bazar.chat.model.MessagePageResponse;
import org.bazar.chat.model.MessageResponse;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MessageControllerIntegrationTest extends AbstractControllerIntegrationTest {
    private static final TypeReference<MessagePageResponse> TYPE_REF_MESSAGE_DTO = new TypeReference<>() {};
    private static final String CONTENT1 = "content1";
    private static final String CONTENT2 = "content2";

    @Test
    void createMessage_success() throws Exception {
        Chat chat = testDataHelper.createChatWith(ChatBuilder.DEFAULT_SPACE_ID);

        restTestUtil.postPerform(
                String.format(CREATE_MESSAGE_API_URL, chat.getId()),
                Map.of(),
                CreateMessageRequestBuilder.buildDefault(),
                TYPE_REFERENCE_UNIT,
                Map.of(),
                status().isOk()
        );

        List<Message> messages = messageRepository.findAll();
        Message message = messages.getFirst();
        assertEquals(1, messages.size());
        assertEquals(chat.getId(), message.getChat().getId());
        assertEquals(CreateMessageRequestBuilder.DEFAULT_CONTENT, message.getContent());
    }

    @Test
    void createMessage_chatNotFound() throws Exception {
        restTestUtil.postPerform(
                String.format(CREATE_MESSAGE_API_URL, "1"),
                Map.of(),
                CreateMessageRequestBuilder.buildDefault(),
                TYPE_REFERENCE_STRING,
                Map.of(),
                status().isNotFound()
        );
    }

    @Test
    void getMessagesByChatId_success() throws Exception {
        Chat chat = testDataHelper.createChatWith(ChatBuilder.DEFAULT_SPACE_ID);
        testDataHelper.createMessageWith(chat, CONTENT1, JwtBuilder.TEST_USER_ID);
        testDataHelper.createMessageWith(chat, CONTENT2, JwtBuilder.TEST_USER_ID);

        List<MessageResponse> response = restTestUtil.getPerform(
                String.format(GET_MESSAGES_BY_CHAT_ID, chat.getId()),
                Map.of(),
                TYPE_REF_MESSAGE_DTO,
                Map.of(),
                status().isOk()
        ).getContent();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertTrue(response.stream().allMatch(dto -> JwtBuilder.TEST_USER_ID.equals(dto.getUserId())));
        MessageResponse first = response.getFirst();
        assertEquals(CONTENT2, first.getContent());
        MessageResponse second = response.get(1);
        assertEquals(CONTENT1, second.getContent());
    }
}
