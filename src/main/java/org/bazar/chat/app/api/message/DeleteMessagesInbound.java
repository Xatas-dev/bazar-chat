package org.bazar.chat.app.api.message;

import java.util.List;

/**
 * Входной интерфейс для удаления сообщений в чате
 */
public interface DeleteMessagesInbound {
    /**
     * Удалить сообщения в чате
     *
     * @param chatId Идентификатор чата
     * @param messageIds Идентификаторы сообщений
     */
    void execute(Long chatId, List<Long> messageIds);
}
