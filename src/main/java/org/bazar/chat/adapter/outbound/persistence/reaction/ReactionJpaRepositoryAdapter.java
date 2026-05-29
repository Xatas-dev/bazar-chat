package org.bazar.chat.adapter.outbound.persistence.reaction;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.reaction.ReactionRepository;
import org.bazar.chat.domain.reaction.Reaction;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Имплементация репозитория для работы с сущностью реакции
 */
@Component
@RequiredArgsConstructor
public class ReactionJpaRepositoryAdapter implements ReactionRepository {
    private final ReactionJpaRepository reactionJpaRepository;

    @Override
    public List<Reaction> getAllReactions() {
        return reactionJpaRepository.findAll();
    }

    @Override
    public Reaction getReference(Long reactionId) {
        return reactionJpaRepository.getReferenceById(reactionId);
    }
}
