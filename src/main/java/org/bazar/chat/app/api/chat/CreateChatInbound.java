package org.bazar.chat.app.api.chat;

import org.bazar.chat.app.api.chat.dto.GetChatDto;

/**
 * Входной интерфейс для создания чата
 */
public interface CreateChatInbound {
    /**
     * Создать чат по идентификатору пространства
     *
     * @param spaceId Идентификатор пространства
     * @return DTO созданного чата
     */
    GetChatDto execute(Long spaceId);
}
