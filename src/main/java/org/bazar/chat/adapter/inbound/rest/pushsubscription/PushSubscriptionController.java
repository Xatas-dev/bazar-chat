package org.bazar.chat.adapter.inbound.rest.pushsubscription;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.adapter.inbound.rest.pushsubscription.dto.V1PushPublicKeyResponse;
import org.bazar.chat.adapter.inbound.rest.pushsubscription.dto.V1PushSubscriptionRequest;
import org.bazar.chat.adapter.inbound.rest.pushsubscription.dto.V1PushUnsubscribeRequest;
import org.bazar.chat.app.api.pushsubscription.GetPushPublicKeyInbound;
import org.bazar.chat.app.api.pushsubscription.SubscribeToPushNotificationsInbound;
import org.bazar.chat.app.api.pushsubscription.UnsubscribeToPushNotificationInbound;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PushSubscriptionController implements PushSubscriptionControllerSwagger {
    private final RestPushSubscriptionMapper restPushSubscriptionMapper;
    private final GetPushPublicKeyInbound getPushPublicKeyInbound;
    private final SubscribeToPushNotificationsInbound subscribeToPushNotificationsInbound;
    private final UnsubscribeToPushNotificationInbound unsubscribeToPushNotificationInbound;

    public ResponseEntity<V1PushPublicKeyResponse> getPushPublicKey() {
        String publicKey = getPushPublicKeyInbound.execute();
        return ResponseEntity.ok(restPushSubscriptionMapper.toV1PushPublicKeyResponse(publicKey));
    }

    public ResponseEntity<Void> subscribeToPushNotifications(V1PushSubscriptionRequest pushSubscriptionRequest) {
        subscribeToPushNotificationsInbound.execute(restPushSubscriptionMapper.toSubscribeToPushNotificationDto(pushSubscriptionRequest));
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> unsubscribeFromPushNotifications(V1PushUnsubscribeRequest pushUnsubscribeRequest) {
        unsubscribeToPushNotificationInbound.execute(pushUnsubscribeRequest.endpoint());
        return ResponseEntity.ok().build();
    }
}
