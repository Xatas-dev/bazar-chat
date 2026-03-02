package org.bazar.chat.adapter.inbound.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bazar.chat.app.api.message.DeleteExpiredMessagesInbound;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Планировщик по удалению сообщений
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageDeletingScheduler {
    private final DeleteExpiredMessagesInbound deleteExpiredMessagesInbound;

    @Scheduled(cron = "${settings.scheduler.message.deleteMessages}")
    public void deleteExpiredMessages() {
        log.info("DeleteMessages scheduler is starting...");
        deleteExpiredMessagesInbound.execute();
    }
}
