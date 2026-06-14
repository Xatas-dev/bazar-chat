package org.bazar.chat.app.api.message;

import org.bazar.chat.app.api.message.dto.GetMessageDto;
import org.springframework.data.domain.Page;
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
    Page<GetMessageDto> execute(Long chatId, Pageable pageable);
}
