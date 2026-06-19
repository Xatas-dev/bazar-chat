package org.bazar.chat.adapter.outbound.webpush;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.bazar.chat.app.api.pushsubscription.PushSubscriptionRepository;
import org.bazar.chat.domain.pushsubscription.PushSubscription;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для отправки push-уведомлений
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebPushService {
    private final PushSubscriptionRepository pushSubscriptionRepository;
    private final PushService pushService;
    private final ObjectMapper objectMapper;

    public void send(UUID userId, PushPayloadDto payload) {
        List<PushSubscription> subscriptions = pushSubscriptionRepository.findByUserId(userId);

        for (PushSubscription subscription : subscriptions) {
            send(subscription, payload);
        }
    }

    // =================================================================================================================
    // = Implementation
    // =================================================================================================================

    private void send(PushSubscription subscription, PushPayloadDto payload) {
        try {
            Notification notification = new Notification(
                    subscription.getEndpoint(),
                    subscription.getP256dh(),
                    subscription.getAuth(),
                    objectMapper.writeValueAsBytes(payload)
            );

            int statusCode = pushService.send(notification).getStatusLine().getStatusCode();
            if (statusCode == 404 || statusCode == 410) {
                pushSubscriptionRepository.delete(subscription);
            }
        } catch (Exception e) {
            log.error("Failed to send web push notification", e);
        }
    }
}
