package org.bazar.chat.adapter.inbound.rest.pushsubscription;

import org.bazar.chat.app.api.pushsubscription.dto.SubscribeToPushNotificationDto;
import org.bazar.chat.model.PushSubscriptionRequest;
import org.mapstruct.Mapper;

@Mapper
public interface RestPushSubscriptionMapper {
    SubscribeToPushNotificationDto toSubscribeToPushNotificationDto(PushSubscriptionRequest pushSubscriptionRequest);
}
