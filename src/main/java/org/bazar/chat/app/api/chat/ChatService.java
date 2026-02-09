package org.bazar.chat.app.api.chat;

import org.bazar.chat.app.api.chat.dto.GetChatDto;

/**
 * Сервис для работы со сценариями по сущности Чат
 */
public interface ChatService {
    /**
     * Создать чат
     *
     * @param spaceId Идентификатор пространства
     * @return DTO созданного чата
     */
    GetChatDto createChat(Long spaceId);

    /**
     * Получить чат по идентификатору пространства
     *
     * @param spaceId Идентификатор пространства
     * @return DTO полученного чата
     */
    GetChatDto getChatBySpaceId(Long spaceId);

    /**
     * Удалить чат по идентификатору пространства
     *
     * @param spaceId Идентификатор пространства
     */
    void deleteChatBySpaceId(Long spaceId);
}
