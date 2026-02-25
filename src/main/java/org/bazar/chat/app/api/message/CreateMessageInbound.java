package org.bazar.chat.app.api.message;

import org.bazar.chat.app.api.message.dto.CreateMessageDto;

/**
 * Входной интерфейс создания сообщения
 */
public interface CreateMessageInbound {
    /**
     * Создать сообщение
     *
     * @param dto Данные для создания сообщения
     */
    void execute(CreateMessageDto dto);
}
