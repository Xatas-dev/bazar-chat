package org.bazar.chat.it.controller;

import builder.JwtBuilder;
import builder.V1CreateMessageRequestBuilder;
import builder.V1DeleteMessageRequestBuilder;
import builder.V1UpdateChatMessageRequestBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import org.bazar.chat.adapter.inbound.rest.message.dto.V1DeleteMessageRequest;
import org.bazar.chat.adapter.inbound.rest.message.dto.V1GetMessageResponse;
import org.bazar.chat.adapter.inbound.rest.message.dto.V1UpdateChatMessageRequest;
import org.bazar.chat.app.api.message.dto.AllowedActions;
import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.domain.message.Message;
import org.bazar.chat.domain.reaction.Reaction;
import org.bazar.chat.it.testutil.RestPageImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static builder.ChatBuilder.DEFAULT_SPACE_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MessageControllerIntegrationTest extends AbstractControllerIntegrationTest {
    private static final TypeReference<RestPageImpl<V1GetMessageResponse>> TYPE_REF_PAGE_V1_GET_MESSAGE_RESPONSE = new TypeReference<>() {};
    private static final String CONTENT1 = "content1content1content1content1content1content1content1content1content1content1";
    private static final String CONTENT2 = "content2";
    private static final String CONTENT3 = "content3";
    private static final Long REACTION_1ID = 1L;

    @BeforeEach
    void setUp() {
        wireMockTestHelper.startMockBazarPersonaServer();
        wireMockTestHelper.startMockBazarSpaceServer();
        wireMockTestHelper.stubBazarPersonaGetUsers_200(List.of(JwtBuilder.TEST_USER_ID), "/MessageControllerIntegrationTest/PersonaGetUsersResponse.json");
        wireMockTestHelper.stubBazarSpaceGetUsers_200(DEFAULT_SPACE_ID.toString(), "/MessageControllerIntegrationTest/SpaceGetUsersResponse.json");
        when(bazarAuthorizationClient.authorize(any())).thenReturn(true);
    }

    @Test
    @DisplayName("Успешное создание сообщения c ответом")
    void createMessage_successWithReply() throws Exception {
        Long chatId = testDataHelper.createRealChat().id();
        Chat chat = chatJpaRepository.findById(chatId).orElseThrow();
        testDataHelper.createRealMessage(chatId);
        Message messageToReply = messageJpaRepository.findAll().getFirst();

        restTestUtil.postPerform(
                String.format(CREATE_MESSAGE_API_URL, DEFAULT_SPACE_ID, chat.getId()),
                Map.of(),
                V1CreateMessageRequestBuilder.buildWith(messageToReply.getId()),
                TYPE_REFERENCE_VOID,
                Map.of(),
                status().isOk()
        );

        List<Message> messages = messageJpaRepository.findAll();
        Message resultMessage = messages.stream().filter(message -> !message.getId().equals(messageToReply.getId())).findFirst().orElseThrow();
        assertEquals(2, messages.size());
        assertEquals(chat.getId(), resultMessage.getChat().getId());
        assertNull(resultMessage.getContent());
        assertNotNull(resultMessage.getReplyMessage());
        assertEquals(chatId, resultMessage.getChat().getId());
        assertNull(resultMessage.getContent());
        assertNotNull(resultMessage.getEncryptedContent());
        assertEquals(12, resultMessage.getIv().length);
        assertNotNull(resultMessage.getEncryptionVersion());
    }

    @Test
    @DisplayName("Успешное создание сообщения")
    void createMessage_success() throws Exception {
        Long chatId = testDataHelper.createRealChat().id();

        restTestUtil.postPerform(
                String.format(CREATE_MESSAGE_API_URL, DEFAULT_SPACE_ID, chatId),
                Map.of(),
                V1CreateMessageRequestBuilder.buildDefault(),
                TYPE_REFERENCE_VOID,
                Map.of(),
                status().isOk()
        );

        List<Message> messages = messageJpaRepository.findAll();
        Message resultMessage = messages.getFirst();
        assertEquals(1, messages.size());
        assertEquals(chatId, resultMessage.getChat().getId());
        assertNull(resultMessage.getContent());
        assertNotNull(resultMessage.getEncryptedContent());
        assertEquals(12, resultMessage.getIv().length);
        assertNotNull(resultMessage.getEncryptionVersion());
    }

    @Test
    @DisplayName("Неуспешное создание сообщения - пользователь не имеет прав на отправку сообщений")
    void createMessage_unauthorized() throws Exception {
        when(bazarAuthorizationClient.authorize(any())).thenReturn(false);
        Chat chat = testDataHelper.createChatWith(DEFAULT_SPACE_ID);

        restTestUtil.postPerform(
                String.format(CREATE_MESSAGE_API_URL, DEFAULT_SPACE_ID, chat.getId()),
                Map.of(),
                V1CreateMessageRequestBuilder.buildDefault(),
                TYPE_REFERENCE_STRING,
                Map.of(),
                status().isForbidden()
        );

        List<Message> messages = messageJpaRepository.findAll();
        assertEquals(0, messages.size());
    }

    @Test
    @DisplayName("Неуспешное создание сообщения - внутренняя ошибка")
    void createMessage_internalException() throws Exception {
        when(bazarAuthorizationClient.authorize(any())).thenThrow(new RuntimeException("Authorization client internal server error"));
        Chat chat = testDataHelper.createChatWith(DEFAULT_SPACE_ID);

        restTestUtil.postPerform(
                String.format(CREATE_MESSAGE_API_URL, DEFAULT_SPACE_ID, chat.getId()),
                Map.of(),
                V1CreateMessageRequestBuilder.buildDefault(),
                TYPE_REFERENCE_STRING,
                Map.of(),
                status().isInternalServerError()
        );

        List<Message> messages = messageJpaRepository.findAll();
        assertEquals(0, messages.size());
    }

    @Test
    @DisplayName("Неуспешное создание сообщения - чат не найден")
    void createMessage_chatNotFound() throws Exception {
        restTestUtil.postPerform(
                String.format(CREATE_MESSAGE_API_URL, DEFAULT_SPACE_ID, "1"),
                Map.of(),
                V1CreateMessageRequestBuilder.buildDefault(),
                TYPE_REFERENCE_STRING,
                Map.of(),
                status().isNotFound()
        );
    }

    @Test
    @DisplayName("Успешное получение сообщений по идентификатору чата")
    void getMessagesByChatId_success() throws Exception {
        Long chatId = testDataHelper.createRealChat().id();
        Chat chat = chatJpaRepository.findById(chatId).orElseThrow();
        Message replyedMessage = testDataHelper.createRealMessage(chatId, CONTENT1, JwtBuilder.TEST_USER_ID, true);
        Message commonMessage = testDataHelper.createRealMessage(chatId, CONTENT2, UUID.fromString("baed9d65-046e-4616-9515-1e4237134f31"), true, replyedMessage.getId());
        testDataHelper.createRealMessage(chatId, CONTENT3, JwtBuilder.TEST_USER_ID, false);
        Reaction reaction = reactionJpaRepository.findById(REACTION_1ID).orElseThrow();
        testDataHelper.createMessageReactionWith(commonMessage, reaction, UUID.randomUUID());
        testDataHelper.createMessageReactionWith(commonMessage, reaction, JwtBuilder.TEST_USER_ID);
        testDataHelper.createMessageReactionWith(replyedMessage, reaction, UUID.randomUUID());

        List<V1GetMessageResponse> response = restTestUtil.getPerform(
                String.format(GET_MESSAGES_BY_CHAT_ID, DEFAULT_SPACE_ID, chat.getId()),
                Map.of(),
                TYPE_REF_PAGE_V1_GET_MESSAGE_RESPONSE,
                Map.of(),
                status().isOk()
        ).getContent();

        assertNotNull(response);
        assertEquals(2, response.size());
        V1GetMessageResponse first = response.getFirst();
        assertEquals(CONTENT2, first.content());
        assertFalse(first.allowedActions().contains(AllowedActions.DELETE.name()));
        assertFalse(first.allowedActions().contains(AllowedActions.EDIT.name()));
        assertEquals(1, first.reactions().size());
        assertEquals(2, first.reactions().getFirst().count());
        assertTrue(first.reactions().getFirst().reactedByMe());
        assertNotNull(first.reply());
        V1GetMessageResponse second = response.get(1);
        assertEquals(CONTENT1, second.content());
        assertNull(second.reply());
        assertEquals(1, second.reactions().size());
        assertEquals(1, second.reactions().getFirst().count());
        assertFalse(second.reactions().getFirst().reactedByMe());
    }

    @Test
    @DisplayName("Успешное получение сообщений пустого чата")
    void getMessageByChatId_emptyChat() throws Exception {
        Long chatId = testDataHelper.createRealChat().id();
        Chat chat = chatJpaRepository.findById(chatId).orElseThrow();

        List<V1GetMessageResponse> response = restTestUtil.getPerform(
                String.format(GET_MESSAGES_BY_CHAT_ID, DEFAULT_SPACE_ID, chat.getId()),
                Map.of(),
                TYPE_REF_PAGE_V1_GET_MESSAGE_RESPONSE,
                Map.of(),
                status().isOk()
        ).getContent();

        assertEquals(0, response.size());
    }

    @Test
    @DisplayName("Успешное удаление сообщений")
    void deleteMessages_success() throws Exception {
        Chat chat = testDataHelper.createChatWith(DEFAULT_SPACE_ID);
        Message message1 = testDataHelper.createMessageWith(chat, CONTENT1, JwtBuilder.TEST_USER_ID, true);
        Message message2 = testDataHelper.createMessageWith(chat, CONTENT2, JwtBuilder.TEST_USER_ID, false);
        V1DeleteMessageRequest request = V1DeleteMessageRequestBuilder.buildWith(List.of(message1.getId(), message2.getId()));

        restTestUtil.deletePerform(
                String.format(DELETE_MESSAGE_BY_IDS, DEFAULT_SPACE_ID, chat.getId()),
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
    @DisplayName("Неуспешное удаление сообщений - сообщение не принадлежит пользователю")
    void deleteMessage_forbidden() throws Exception {
        Chat chat = testDataHelper.createChatWith(DEFAULT_SPACE_ID);
        Message message = testDataHelper.createMessageWith(chat, CONTENT1, UUID.randomUUID(), true);
        V1DeleteMessageRequest request = V1DeleteMessageRequestBuilder.buildWith(List.of(message.getId()));

        restTestUtil.deletePerform(
                String.format(DELETE_MESSAGE_BY_IDS, DEFAULT_SPACE_ID, chat.getId()),
                Map.of(),
                request,
                TYPE_REFERENCE_STRING,
                Map.of(),
                status().isForbidden()
        );

        Message messageResult = messageJpaRepository.findById(message.getId()).orElseThrow();
        assertTrue(messageResult.getVisible());
    }

    @Test
    @DisplayName("Успешное редактирование сообщения")
    void updateMessage_success() throws Exception {
        Long chatId = testDataHelper.createRealChat().id();
        Message message = testDataHelper.createRealMessage(chatId, CONTENT1, JwtBuilder.TEST_USER_ID, true);
        V1UpdateChatMessageRequest request = V1UpdateChatMessageRequestBuilder.buildWith(CONTENT2);

        restTestUtil.patchPerform(
                String.format(UPDATE_MESSAGE_API_URL, DEFAULT_SPACE_ID, chatId, message.getId()),
                Map.of(),
                request,
                TYPE_REFERENCE_VOID,
                Map.of(),
                status().isOk()
        );

        Message messageResult = messageJpaRepository.findById(message.getId()).orElseThrow();
        Chat chat = chatJpaRepository.findById(chatId).orElseThrow();
        String newContent = chatEncryptionService.decrypt(messageResult, chatEncryptionService.getDek(chat));
        assertEquals(CONTENT2, newContent);
    }
}
