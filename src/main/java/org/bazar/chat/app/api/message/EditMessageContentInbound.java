package org.bazar.chat.app.api.message;

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
