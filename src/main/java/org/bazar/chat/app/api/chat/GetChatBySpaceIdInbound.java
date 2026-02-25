package org.bazar.chat.app.api.chat;

import org.bazar.chat.app.api.chat.dto.GetChatDto;

/**
 * Входной интерфейс для получения информации по чату по идентификатору пространства
 */
public interface GetChatBySpaceIdInbound {
    /**
     * Получить информацию по чату по идентификатору пространства
     *
     * @param spaceId Идентификатор пространства
     * @return DTO информации по чату
     */
    GetChatDto execute(Long spaceId);
}
