package org.bazar.chat.app.api.pushsubscription;

import org.bazar.chat.domain.pushsubscription.PushSubscription;

import java.util.List;
import java.util.UUID;

/**
 * Репозиторий для работы с сущностью подписки на push-уведомления
 */
public interface PushSubscriptionRepository {
    /**
     * Существует ли подписка в системе
     *
     * @param userId Идентификатор пользоватея
     * @param endpoint Эндпоинт, по которому отправляется уведомление
     * @return Результат проверки
     */
    boolean existsByUserIdAndEndpoint(UUID userId, String endpoint);

    /**
     * Сохранить подписку
     *
     * @param pushSubscription Подписка на push-уведомление
     */
    void save(PushSubscription pushSubscription);

    /**
     * Удалить подписку
     *
     * @param pushSubscription Подписка на push-уведомление
     */
    void delete(PushSubscription pushSubscription);

    /**
     * Получить все подписки пользователя
     *
     * @param userId Идентификатор пользователя
     * @return Список подписок
     */
    List<PushSubscription> findByUserId(UUID userId);

    /**
     * Удалить подписку по эндпоинту
     *
     * @param endpoint Эндпоинт, на который отправляется уведомление
     */
    void deleteByEndpoint(String endpoint);
}
