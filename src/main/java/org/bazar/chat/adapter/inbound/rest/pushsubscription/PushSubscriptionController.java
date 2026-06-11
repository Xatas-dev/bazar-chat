package org.bazar.chat.adapter.inbound.rest.pushsubscription;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.api.PushNotificationsApi;
import org.bazar.chat.app.api.pushsubscription.GetPushPublicKeyInbound;
import org.bazar.chat.app.api.pushsubscription.SubscribeToPushNotificationsInbound;
import org.bazar.chat.app.api.pushsubscription.UnsubscribeToPushNotificationInbound;
import org.bazar.chat.model.PushPublicKeyResponse;
import org.bazar.chat.model.PushSubscriptionRequest;
import org.bazar.chat.model.PushUnsubscribeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PushSubscriptionController implements PushNotificationsApi {
    private final RestPushSubscriptionMapper restPushSubscriptionMapper;
    private final GetPushPublicKeyInbound getPushPublicKeyInbound;
    private final SubscribeToPushNotificationsInbound subscribeToPushNotificationsInbound;
    private final UnsubscribeToPushNotificationInbound unsubscribeToPushNotificationInbound;

    @Override
    public ResponseEntity<PushPublicKeyResponse> getPushPublicKey() {
        return ResponseEntity.ok(new PushPublicKeyResponse(getPushPublicKeyInbound.execute()));
    }

    @Override
    public ResponseEntity<Void> subscribeToPushNotifications(PushSubscriptionRequest pushSubscriptionRequest) {
        subscribeToPushNotificationsInbound.execute(restPushSubscriptionMapper.toSubscribeToPushNotificationDto(pushSubscriptionRequest));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> unsubscribeFromPushNotifications(PushUnsubscribeRequest pushUnsubscribeRequest) {
        unsubscribeToPushNotificationInbound.execute(pushUnsubscribeRequest.getEndpoint());
        return ResponseEntity.ok().build();
    }
}
