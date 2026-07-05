package org.bazar.chat.app.impl.pushsubscription;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.pushsubscription.PushSubscriptionRepository;
import org.bazar.chat.app.api.pushsubscription.SubscribeToPushNotificationsInbound;
import org.bazar.chat.app.api.pushsubscription.dto.SubscribeToPushNotificationDto;
import org.bazar.chat.app.api.auth.AuthenticationService;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Реализация интерфейса для регистрации новой подписки на push-уведомления
 */
@Component
@RequiredArgsConstructor
public class SubscribeToPushNotificationsUseCase implements SubscribeToPushNotificationsInbound {
    private final PushSubscriptionRepository pushSubscriptionRepository;
    private final AuthenticationService authenticationService;
    private final PushSubscriptionMapper pushSubscriptionMapper;

    @Override
    @Transactional
    public void execute(SubscribeToPushNotificationDto dto) {
        UUID userId = authenticationService.getAuthenticatedUserId();

        if (pushSubscriptionRepository.existsByUserIdAndEndpoint(userId, dto.endpoint())) {
            return;
        }

        pushSubscriptionRepository.save(pushSubscriptionMapper.toPushSubscription(dto, userId));
    }
}
