package org.bazar.chat.app.api.pushsubscription;

import org.bazar.chat.app.api.pushsubscription.dto.SubscribeToPushNotificationDto;

/**
 * Входной интерфейс для регистрации новой подписки на push-уведомления
 */
public interface SubscribeToPushNotificationsInbound {
    /**
     * Сохранить новую подписку
     *
     * @param dto DTO для сохранения новой подписки
     */
    void execute(SubscribeToPushNotificationDto dto);
}
