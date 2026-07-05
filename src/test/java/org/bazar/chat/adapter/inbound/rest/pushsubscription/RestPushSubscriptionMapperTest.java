package org.bazar.chat.adapter.inbound.rest.pushsubscription;

import org.bazar.chat.AbstractTest;
import org.bazar.chat.adapter.inbound.rest.pushsubscription.dto.V1PushPublicKeyResponse;
import org.bazar.chat.adapter.inbound.rest.pushsubscription.dto.V1PushSubscriptionRequest;
import org.bazar.chat.app.api.pushsubscription.dto.SubscribeToPushNotificationDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.bazar.chat.TestDataTransformUtil.readObjectFromFile;

public class RestPushSubscriptionMapperTest extends AbstractTest {
    private final RestPushSubscriptionMapper restPushSubscriptionMapper = Mappers.getMapper(RestPushSubscriptionMapper.class);

    @Test
    void toSubscribeToPushNotificationDto() {
        V1PushSubscriptionRequest v1PushSubscriptionRequest = readObjectFromFile(
                "/RestPushSubscriptionMapperTest/V1PushSubscriptionRequest.json",
                V1PushSubscriptionRequest.class
        );

        SubscribeToPushNotificationDto result =
                restPushSubscriptionMapper.toSubscribeToPushNotificationDto(v1PushSubscriptionRequest);

        assertEqualsToFile(result, "/RestPushSubscriptionMapperTest/SubscribeToPushNotificationDto_expected.json");
    }

    @Test
    void toV1PushPublicKeyResponse() {
        V1PushPublicKeyResponse result = restPushSubscriptionMapper.toV1PushPublicKeyResponse("public");

        assertEqualsToFile(result, "/RestPushSubscriptionMapperTest/V1PushPublicKeyResponse_expected.json");
    }
}
