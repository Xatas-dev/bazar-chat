package org.bazar.chat.app.impl.pushsubscription;

import org.bazar.chat.app.api.pushsubscription.dto.SubscribeToPushNotificationDto;
import org.bazar.chat.domain.pushsubscription.PushSubscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper
public interface PushSubscriptionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PushSubscription toPushSubscription(SubscribeToPushNotificationDto dto, UUID userId);
}
