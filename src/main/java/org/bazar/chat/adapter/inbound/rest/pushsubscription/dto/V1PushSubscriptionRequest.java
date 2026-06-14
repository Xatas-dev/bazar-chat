package org.bazar.chat.adapter.inbound.rest.pushsubscription.dto;

public record V1PushSubscriptionRequest(
        String endpoint,
        String p256dh,
        String auth
) {
}
