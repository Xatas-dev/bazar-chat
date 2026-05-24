package org.bazar.chat.adapter.outbound.persistence.reaction;

import org.bazar.chat.domain.reaction.MessageReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

/**
 * Jpa репозиторий для работы с сущностью реакции на сообщение
 */
public interface MessageReactionJpaRepository extends JpaRepository<MessageReaction, Long> {
    boolean existsMessageReactionByMessageIdAndReactionIdAndUserId(Long messageId, Long reactionId, UUID userId);

    @Modifying
    @Query("""
    DELETE FROM MessageReaction mr
    WHERE mr.message.id = :messageId AND mr.reaction.id = :reactionId AND mr.userId = :userId
    """)
    void deleteMessageReaction(Long messageId, Long reactionId, UUID userId);

    long countMessageReactionsByMessageIdAndUserId(Long messageId, UUID userId);

    long countMessageReactionsByMessageIdAndReactionId(Long messageId, Long reactionId);
}
