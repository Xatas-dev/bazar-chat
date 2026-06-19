package org.bazar.chat.app.api.pushsubscription.dto;

/**
 * DTO для сохранения новой подписки
 *
 * @param endpoint Эндпоинт, на который отправляются push-уведомления
 * @param p256dh Открытый ключ пользователя
 * @param auth Приватный ключ пользователя
 */
public record SubscribeToPushNotificationDto(
        String endpoint,
        String p256dh,
        String auth
) {}
