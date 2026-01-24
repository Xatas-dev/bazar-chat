package org.bazar.chat.adapter.outbound.persistence.chat;

import org.bazar.chat.domain.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatJpaRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findBySpaceId(Long spaceId);
}
