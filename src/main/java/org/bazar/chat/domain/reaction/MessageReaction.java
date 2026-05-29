package org.bazar.chat.domain.reaction;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.bazar.chat.domain.DomainObject;
import org.bazar.chat.domain.message.Message;

import java.util.UUID;

/**
 * Jpa сущность Реакция на сообщение
 */
@Getter
@Setter
@Entity
@Table(name = "message_reaction")
public class MessageReaction extends DomainObject {
    /**
     * Сообщение
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;

    /**
     * Реакция
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reaction_id", nullable = false)
    private Reaction reaction;

    /**
     * Идентификатор пользователя
     */
    @Column(name = "user_id")
    private UUID userId;
}
