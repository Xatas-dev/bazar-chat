package org.bazar.chat.app.api.message;

import org.bazar.chat.app.api.message.dto.GetMessagePageDto;
import org.springframework.data.domain.Pageable;

/**
 * Входной интерфейс по получения сообщений чата
 */
public interface GetChatMessagesInbound {
    /**
     * Получить список сообщений чата с пагинацией
     *
     * @param chatId Идентификатор чата
     * @param pageable Информация по пагинации
     * @return Список сообщений чата
     */
    GetMessagePageDto execute(Long chatId, Pageable pageable);
}
