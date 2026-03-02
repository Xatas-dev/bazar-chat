package org.bazar.chat.it.controller;

import builder.ChatBuilder;
import builder.CreateMessageRequestBuilder;
import builder.DeleteMessageRequestBuilder;
import builder.JwtBuilder;
import builder.UpdateChatMessageRequestBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import org.bazar.chat.app.api.message.dto.AllowedActions;
import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.domain.message.Message;
import org.bazar.chat.model.DeleteMessageRequest;
import org.bazar.chat.model.MessagePageResponse;
import org.bazar.chat.model.MessageResponse;
import org.bazar.chat.model.UpdateChatMessageRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MessageControllerIntegrationTest extends AbstractControllerIntegrationTest {
    private static final TypeReference<MessagePageResponse> TYPE_REF_MESSAGE_DTO = new TypeReference<>() {};
    private static final String CONTENT1 = "content1content1content1content1content1content1content1content1content1content1";
    private static final String CONTENT2 = "content2";
    private static final String CONTENT3 = "content3";
    private static final String CONTENT4 = "content4";

    @Test
    @DisplayName("Успешное создание сообщения c ответом")
    void createMessage_successWithReply() throws Exception {
        wireMockTestHelper.startMockBazarPersonaServer();
        wireMockTestHelper.stubBazarPersonaGetUsers_200(List.of(JwtBuilder.TEST_USER_ID), "/MessageControllerIntegrationTest/PersonaGetUsersResponse.json");
        Chat chat = testDataHelper.createChatWith(ChatBuilder.DEFAULT_SPACE_ID);
        Message messageToReply = testDataHelper.createMessageWith(chat, CONTENT1, JwtBuilder.TEST_USER_ID, true);

        restTestUtil.postPerform(
                String.format(CREATE_MESSAGE_API_URL, chat.getId()),
                Map.of(),
                CreateMessageRequestBuilder.buildWith(messageToReply.getId()),
                TYPE_REFERENCE_VOID,
                Map.of(),
                status().isOk()
        );

        List<Message> messages = messageJpaRepository.findAll();
        Message resultMessage = messages.stream().filter(message -> !message.getId().equals(messageToReply.getId())).findFirst().orElseThrow();
        assertEquals(2, messages.size());
        assertEquals(chat.getId(), resultMessage.getChat().getId());
        assertEquals(CreateMessageRequestBuilder.DEFAULT_CONTENT, resultMessage.getContent());
        assertNotNull(resultMessage.getReplyMessage());
        assertEquals(CONTENT1, resultMessage.getReplyMessage().getContent());
    }

    @Test
    @DisplayName("Успешное создание сообщения")
    void createMessage_success() throws Exception {
        wireMockTestHelper.startMockBazarPersonaServer();
        wireMockTestHelper.stubBazarPersonaGetUsers_200(List.of(JwtBuilder.TEST_USER_ID), "/MessageControllerIntegrationTest/PersonaGetUsersResponse.json");
        Chat chat = testDataHelper.createChatWith(ChatBuilder.DEFAULT_SPACE_ID);

        restTestUtil.postPerform(
                String.format(CREATE_MESSAGE_API_URL, chat.getId()),
                Map.of(),
                CreateMessageRequestBuilder.buildDefault(),
                TYPE_REFERENCE_VOID,
                Map.of(),
                status().isOk()
        );

        List<Message> messages = messageJpaRepository.findAll();
        Message resultMessage = messages.getFirst();
        assertEquals(1, messages.size());
        assertEquals(chat.getId(), resultMessage.getChat().getId());
        assertEquals(CreateMessageRequestBuilder.DEFAULT_CONTENT, resultMessage.getContent());
    }

    @Test
    @DisplayName("Неуспешное создание сообщения - чат не найден")
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
    @DisplayName("Успешное получение сообщений по идентификатору чата")
    void getMessagesByChatId_success() throws Exception {
        wireMockTestHelper.startMockBazarPersonaServer();
        wireMockTestHelper.stubBazarPersonaGetUsers_200(List.of(JwtBuilder.TEST_USER_ID), "/MessageControllerIntegrationTest/PersonaGetUsersResponse.json");
        Chat chat = testDataHelper.createChatWith(ChatBuilder.DEFAULT_SPACE_ID);
        Message replyedMessage = testDataHelper.createMessageWith(chat, CONTENT1, JwtBuilder.TEST_USER_ID, true);
        testDataHelper.createMessageWith(chat, CONTENT2, UUID.fromString("baed9d65-046e-4616-9515-1e4237134f31"), true, replyedMessage);
        Message deletedMessage = testDataHelper.createMessageWith(chat, CONTENT3, JwtBuilder.TEST_USER_ID, false);
        testDataHelper.createMessageWith(chat, CONTENT4, JwtBuilder.TEST_USER_ID, true, deletedMessage);

        List<MessageResponse> response = restTestUtil.getPerform(
                String.format(GET_MESSAGES_BY_CHAT_ID, chat.getId()),
                Map.of(),
                TYPE_REF_MESSAGE_DTO,
                Map.of(),
                status().isOk()
        ).getContent();

        assertNotNull(response);
        assertEquals(3, response.size());
        MessageResponse first = response.getFirst();
        assertEquals(CONTENT4, first.getContent());
        assertTrue(first.getAllowedActions().contains(AllowedActions.DELETE.name()));
        assertTrue(first.getAllowedActions().contains(AllowedActions.EDIT.name()));
        assertNull(first.getReply());
        MessageResponse second = response.get(1);
        assertEquals(CONTENT2, second.getContent());
        assertFalse(second.getAllowedActions().contains(AllowedActions.DELETE.name()));
        assertFalse(second.getAllowedActions().contains(AllowedActions.EDIT.name()));
        assertNotNull(second.getReply());
        MessageResponse third = response.get(2);
        assertEquals(CONTENT1, third.getContent());
        assertNull(third.getReply());
    }

    @Test
    @DisplayName("Успешное удаление сообщений")
    void deleteMessages_success() throws Exception {
        Chat chat = testDataHelper.createChatWith(ChatBuilder.DEFAULT_SPACE_ID);
        Message message1 = testDataHelper.createMessageWith(chat, CONTENT1, JwtBuilder.TEST_USER_ID, true);
        Message message2 = testDataHelper.createMessageWith(chat, CONTENT2, JwtBuilder.TEST_USER_ID, false);
        DeleteMessageRequest request = DeleteMessageRequestBuilder.buildWith(List.of(message1.getId(), message2.getId()));

        restTestUtil.deletePerform(
                String.format(DELETE_MESSAGE_BY_IDS, chat.getId()),
                Map.of(),
                request,
                TYPE_REFERENCE_VOID,
                Map.of(),
                status().isOk()
        );

        List<Message> messages = messageJpaRepository.findAll();
        messages.forEach(message -> assertFalse(message.getVisible()));
    }

    @Test
    @DisplayName("Нуспешное удаление сообщений - запрещено текущему пользователю")
    void deleteMessage_forbidden() throws Exception {
        Chat chat = testDataHelper.createChatWith(ChatBuilder.DEFAULT_SPACE_ID);
        Message message = testDataHelper.createMessageWith(chat, CONTENT1, UUID.randomUUID(), true);
        DeleteMessageRequest request = DeleteMessageRequestBuilder.buildWith(List.of(message.getId()));

        restTestUtil.deletePerform(
                String.format(DELETE_MESSAGE_BY_IDS, chat.getId()),
                Map.of(),
                request,
                TYPE_REFERENCE_STRING,
                Map.of(),
                status().isForbidden()
        );

        Message messageResult = messageJpaRepository.findById(message.getId()).get();
        assertTrue(messageResult.getVisible());
    }

    @Test
    @DisplayName("Успешное редактирование сообщения")
    void updateMessage_success() throws Exception {
        Chat chat = testDataHelper.createChatWith(ChatBuilder.DEFAULT_SPACE_ID);
        Message message = testDataHelper.createMessageWith(chat, CONTENT1, JwtBuilder.TEST_USER_ID, true);
        UpdateChatMessageRequest request = UpdateChatMessageRequestBuilder.buildWith(CONTENT2);

        restTestUtil.patchPerform(
                String.format(UPDATE_MESSAGE_API_URL, chat.getId(), message.getId()),
                Map.of(),
                request,
                TYPE_REFERENCE_VOID,
                Map.of(),
                status().isOk()
        );

        Message messageResult = messageJpaRepository.findById(message.getId()).get();
        assertEquals(CONTENT2, messageResult.getContent());
    }
}
