package org.bazar.chat.adapter.outbound.webpush;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.message.dto.event.MessageCreatedEvent;
import org.bazar.chat.app.api.pushsubscription.PresenceService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Слушатель по отправке уведомлений в Web Push
 */
@Component
@RequiredArgsConstructor
@Async("eventExecutor")
public class MessageWebPushListener {
    private final PresenceService presenceService;
    private final WebPushService webPushService;
    private final WebPushMapper webPushMapper;

    @EventListener
    public void handle(MessageCreatedEvent event) {
        Long chatId = event.chatId();
        for (UUID userId : event.chatMembersUuids()) {
            if (presenceService.isViewingChat(userId, chatId)) {
                continue;
            }

            webPushService.send(userId, webPushMapper.toPushPayloadDto(event));
        }
    }
}
