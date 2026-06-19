package org.bazar.chat.app.impl.pushsubscription;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.pushsubscription.PushSubscriptionRepository;
import org.bazar.chat.app.api.pushsubscription.UnsubscribeToPushNotificationInbound;
import org.springframework.stereotype.Component;

/**
 * Реализация интерфейса для отписки от push-уведомлений
 */
@Component
@RequiredArgsConstructor
public class UnsubscribeToPushNotificationUseCase implements UnsubscribeToPushNotificationInbound {
    private final PushSubscriptionRepository pushSubscriptionRepository;

    @Override
    public void execute(String endpoint) {
        pushSubscriptionRepository.deleteByEndpoint(endpoint);
    }
}
