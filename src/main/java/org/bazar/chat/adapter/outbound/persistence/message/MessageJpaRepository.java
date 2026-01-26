package org.bazar.chat.adapter.outbound.persistence.message;

import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.domain.message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageJpaRepository extends JpaRepository<Message, Long> {
    void deleteAllByChat(Chat chat);

    Page<Message> findAllByChatIdAndVisibleTrueOrderByCreatedAtDesc(Long chatId, Pageable pageable);
}
