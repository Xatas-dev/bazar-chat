package org.bazar.chat.app.api.message;

import org.bazar.chat.app.api.message.dto.UpdateMessageDto;

/**
 * Входной интерфейс для редактирования содержимого сообщения
 */
public interface EditMessageContentInbound {
    /**
     * Редактировать содержимое сообщения
     *
     * @param dto DTO для редактирования содержимого сообщения
     */
    void execute(UpdateMessageDto dto);
}
