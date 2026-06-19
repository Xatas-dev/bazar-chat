package org.bazar.chat.app.api.pushsubscription;

/**
 * Входной интерфейс для отписки от push-уведомлений
 */
public interface UnsubscribeToPushNotificationInbound {
    /**
     * Удалить подписку по эндпоинту
     *
     * @param endpoint Эндпоинт, на который отправляются уведомления
     */
    void execute(String endpoint);
}
