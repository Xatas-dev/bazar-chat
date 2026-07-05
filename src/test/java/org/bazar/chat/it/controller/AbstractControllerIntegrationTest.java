package org.bazar.chat.it.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import org.bazar.chat.it.AbstractIntegrationTest;
import org.bazar.chat.it.testutil.RestTestUtil;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractControllerIntegrationTest extends AbstractIntegrationTest {
    protected static final String GET_CHAT_BY_SPACE_API_URL = "/api/v1/spaces/%s/chats";
    protected static final String CREATE_CHAT_API_URL = "/api/v1/spaces/%s/chats";
    protected static final String GET_CHAT_REACTIONS_API_URL = "/api/v1/spaces/%s/chats/%s/reactions";

    protected static final String CREATE_MESSAGE_API_URL = "/api/v1/spaces/%s/chats/%s/messages";
    protected static final String GET_MESSAGES_BY_CHAT_ID = "/api/v1/spaces/%s/chats/%s/messages";
    protected static final String DELETE_MESSAGE_BY_IDS = "/api/v1/spaces/%s/chats/%s/messages";
    protected static final String UPDATE_MESSAGE_API_URL = "/api/v1/spaces/%s/chats/%s/messages/%s";

    protected static final String GET_REACTIONS_BY_MESSAGE_ID = "/api/v1/spaces/%s/chats/%s/messages/%s/reactions/users";
    protected static final String UPDATE_REACTION_API_URL = "/api/v1/spaces/%s/chats/%s/messages/%s/reactions/%s";

    protected static final TypeReference<String> TYPE_REFERENCE_STRING = new TypeReference<>() {};
    protected static final TypeReference<Void> TYPE_REFERENCE_VOID = new TypeReference<>() {};

    @Autowired
    protected RestTestUtil restTestUtil;
}
