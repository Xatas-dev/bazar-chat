package org.bazar.chat.adapter.outbound.persistence.reaction;

import org.bazar.chat.domain.reaction.MessageReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * Jpa репозиторий для работы с сущностью реакция на сообщение
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

    @Query("SELECT mr FROM MessageReaction mr JOIN FETCH mr.reaction WHERE mr.message.id = :message_id")
    List<MessageReaction> findAllByMessageId(@Param("message_id")Long messageId);
}
