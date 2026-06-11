package org.bazar.chat.adapter.outbound.persistence.pushsubscription;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.pushsubscription.PushSubscriptionRepository;
import org.bazar.chat.domain.pushsubscription.PushSubscription;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Реализация репозитория для работы с сущностью подписки на push-уведомления
 */
@Component
@RequiredArgsConstructor
public class PushSubscriptionJpaRepositoryAdapter implements PushSubscriptionRepository {
    private final PushSubscriptionJpaRepository pushSubscriptionJpaRepository;

    @Override
    public boolean existsByUserIdAndEndpoint(UUID userId, String endpoint) {
        return pushSubscriptionJpaRepository.existsByUserIdAndEndpoint(userId, endpoint);
    }

    @Override
    public void save(PushSubscription pushSubscription) {
        pushSubscriptionJpaRepository.save(pushSubscription);
    }

    @Override
    public void delete(PushSubscription pushSubscription) {
        pushSubscriptionJpaRepository.delete(pushSubscription);
    }

    @Override
    public List<PushSubscription> findByUserId(UUID userId) {
        return pushSubscriptionJpaRepository.findAllByUserId(userId);
    }

    @Override
    public void deleteByEndpoint(String endpoint) {
        pushSubscriptionJpaRepository.deleteByEndpoint(endpoint);
    }
}
