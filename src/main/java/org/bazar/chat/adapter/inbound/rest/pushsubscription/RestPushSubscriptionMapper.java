package org.bazar.chat.adapter.inbound.rest.pushsubscription;

import org.bazar.chat.adapter.inbound.rest.pushsubscription.dto.V1PushPublicKeyResponse;
import org.bazar.chat.adapter.inbound.rest.pushsubscription.dto.V1PushSubscriptionRequest;
import org.bazar.chat.app.api.pushsubscription.dto.SubscribeToPushNotificationDto;
import org.mapstruct.Mapper;

@Mapper
public interface RestPushSubscriptionMapper {
    SubscribeToPushNotificationDto toSubscribeToPushNotificationDto(V1PushSubscriptionRequest pushSubscriptionRequest);

    V1PushPublicKeyResponse toV1PushPublicKeyResponse(String publicKey);
}
