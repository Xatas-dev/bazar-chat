package org.bazar.chat.app.api.message;

/**
 * Входной интерфейс удаления истекших сообщений
 */
public interface DeleteExpiredMessagesInbound {
    /**
     * Удалить истекшие сообщения
     */
    void execute();
}
