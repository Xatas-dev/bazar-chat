package org.bazar.chat.app.impl.pushsubscription;

import org.bazar.chat.AbstractTest;
import org.bazar.chat.app.api.pushsubscription.dto.SubscribeToPushNotificationDto;
import org.bazar.chat.domain.pushsubscription.PushSubscription;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.bazar.chat.TestDataTransformUtil.readObjectFromFile;

public class PushSubscriptionMapperTest extends AbstractTest {
    private final PushSubscriptionMapper pushSubscriptionMapper = Mappers.getMapper(PushSubscriptionMapper.class);

    @Test
    void toPushSubscription() {
        SubscribeToPushNotificationDto subscribeToPushNotificationDto = readObjectFromFile(
                "/PushSubscriptionMapperTest/SubscribeToPushNotificationDto.json",
                SubscribeToPushNotificationDto.class
        );

        PushSubscription result = pushSubscriptionMapper.toPushSubscription(
                subscribeToPushNotificationDto,
                UUID.fromString("3af13b7c-0c0e-4a58-878e-67289ef73089")
        );

        assertEqualsToFile(result, "/PushSubscriptionMapperTest/PushSubscription_expected.json");
    }
}
