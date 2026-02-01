package org.bazar.chat.it.app.impl.message.schedule;

import builder.ChatBuilder;
import builder.MessageBuilder;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.bazar.chat.app.impl.message.schedule.MessageDeletingScheduler;
import org.bazar.chat.domain.DomainObject;
import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.domain.message.Message;
import org.bazar.chat.it.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MessageDeletingSchedulerIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MessageDeletingScheduler messageDeletingScheduler;

    @Test
    @Transactional
    void deleteExpiredMessages_success() {
        Chat chat = ChatBuilder.buildDefault();
        Message message1 = MessageBuilder.buildWith(chat);
        Message message2 = MessageBuilder.buildWith(chat);
        Message message3 = MessageBuilder.buildWith(chat);
        message1.setVisible(false);
        message2.setVisible(false);
        chatJpaRepository.save(chat);
        messageJpaRepository.saveAll(List.of(message1, message2, message3));
        setMessageUpdatedAt(message2, Instant.now().minus(8, DAYS));

        messageDeletingScheduler.deleteExpiredMessages();

        List<Message> messages = messageJpaRepository.findAll();
        assertEquals(2, messages.size());
        assertFalse(messages.stream()
                .map(DomainObject::getId)
                .anyMatch(id -> id.equals(message2.getId())));
    }

    // =================================================================================================================
    // = Implementation
    // =================================================================================================================

    private void setMessageUpdatedAt(Message message, Instant updatedAt) {
        entityManager.createQuery("""
            update Message msg
            set msg.updatedAt = :time
            where msg.id = :id
        """)
                .setParameter("time", updatedAt)
                .setParameter("id", message.getId())
                .executeUpdate();

        entityManager.clear();
    }
}
