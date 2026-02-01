package org.bazar.chat.app.impl.message.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bazar.chat.app.api.message.MessageService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageDeletingScheduler {
    private final MessageService messageService;

    @Scheduled(cron = "${scheduler.message.deleteMessages}")
    public void deleteExpiredMessages() {
        log.info("DeleteMessages scheduler is starting...");
        messageService.deleteExpiredMessages();
    }
}
