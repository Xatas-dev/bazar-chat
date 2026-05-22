package org.bazar.chat.adapter.outbound.persistence.reaction;

import org.bazar.chat.domain.reaction.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Jpa репозиторий для работы с сущностью реакции
 */
public interface ReactionJpaRepository extends JpaRepository<Reaction, Long> {
}
