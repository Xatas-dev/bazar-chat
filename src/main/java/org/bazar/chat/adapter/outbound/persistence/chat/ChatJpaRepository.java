package org.bazar.chat.adapter.outbound.persistence.chat;

import org.bazar.chat.domain.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Jpa репозиторий для работы с сущностью чата
 */
public interface ChatJpaRepository extends JpaRepository<Chat, Long> {
    /**
     * Найти чат по идентификатору пространства
     *
     * @param spaceId Идентификатор пространства
     * @return Optional чата
     */
    Optional<Chat> findBySpaceId(Long spaceId);
}
