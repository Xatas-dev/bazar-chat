package org.bazar.chat.app.impl.message;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.message.DeleteExpiredMessagesInbound;
import org.bazar.chat.app.api.message.MessageRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Реализация интерфейса удаления истекших сообщений
 */
@Component
@RequiredArgsConstructor
public class DeleteExpiredMessagesUseCase implements DeleteExpiredMessagesInbound {
    private final MessageRepository messageRepository;

    @Override
    @Transactional
    public void execute() {
        Instant threshold = Instant.now().minus(7, ChronoUnit.DAYS);
        messageRepository.deleteInvisibleMessagesByUpdatedAt(threshold);
    }
}
