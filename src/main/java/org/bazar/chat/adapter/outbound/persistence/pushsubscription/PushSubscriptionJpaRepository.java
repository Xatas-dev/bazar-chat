package org.bazar.chat.adapter.outbound.persistence.pushsubscription;

import org.bazar.chat.domain.pushsubscription.PushSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Jpa репозиторий для работы с сущностью подписки на push-уведомления
 */
public interface PushSubscriptionJpaRepository extends JpaRepository<PushSubscription, Long> {
    /**
     * Существует ли подписка в системе
     *
     * @param userId Идентификатор пользоватея
     * @param endpoint Эндпоинт, по которому отправляется уведомление
     * @return Результат проверки
     */
    boolean existsByUserIdAndEndpoint(UUID userId, String endpoint);

    /**
     * Получить все подписки пользователя
     *
     * @param userId Идентификатор пользователя
     * @return Список подписок
     */
    List<PushSubscription> findAllByUserId(UUID userId);

    /**
     * Удалить подписку по эндпоинту
     *
     * @param endpoint Эндпоинт, на который отправляется уведомление
     */
    void deleteByEndpoint(String endpoint);
}
