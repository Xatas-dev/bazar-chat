package org.bazar.chat.it.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import kotlin.Unit;
import org.bazar.chat.it.AbstractIntegrationTest;
import org.bazar.chat.it.testutil.RestTestUtil;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractControllerIntegrationTest extends AbstractIntegrationTest {
    protected static final String GET_CHAT_BY_SPACE_API_URL = "/chats";
    protected static final String CREATE_CHAT_API_URL = "/chats";
    protected static final String CREATE_MESSAGE_API_URL = "/chats/%s/messages";
    protected static final String GET_MESSAGES_BY_CHAT_ID = "/chats/%s/messages";
    protected static final TypeReference<String> TYPE_REFERENCE_STRING = new TypeReference<>() {};
    protected static final TypeReference<Unit> TYPE_REFERENCE_UNIT = new TypeReference<>() {};


    @Autowired
    protected RestTestUtil restTestUtil;
}
