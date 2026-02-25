package org.bazar.chat.app.api.chat;

public interface DeleteChatBySpaceIdInbound {
    /**
     * Удалить чат по идентификатору пространства
     *
     * @param spaceId Идентификатор пространства
     */
    void execute(Long spaceId);
}
