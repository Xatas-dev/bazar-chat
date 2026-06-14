package org.bazar.chat.adapter.inbound.rest.pushsubscription;

import io.swagger.v3.oas.annotations.Operation;
import org.bazar.chat.adapter.inbound.rest.pushsubscription.dto.V1PushPublicKeyResponse;
import org.bazar.chat.adapter.inbound.rest.pushsubscription.dto.V1PushSubscriptionRequest;
import org.bazar.chat.adapter.inbound.rest.pushsubscription.dto.V1PushUnsubscribeRequest;
import org.springframework.http.ResponseEntity;

public interface PushSubscriptionControllerSwagger {
    @Operation(summary = "Get VAPID public key", description = "Returns VAPID public key used for web push subscriptions")
    ResponseEntity<V1PushPublicKeyResponse> getPushPublicKey();

    @Operation(summary = "Register web push subscription", description = "Register or update current user's web push subscription")
    ResponseEntity<Void> subscribeToPushNotifications(V1PushSubscriptionRequest pushSubscriptionRequest);

    @Operation(summary = "Remove web push subscription", description = "Remove current user's web push subscription")
    ResponseEntity<Void> unsubscribeFromPushNotifications(V1PushUnsubscribeRequest pushUnsubscribeRequest);
}
