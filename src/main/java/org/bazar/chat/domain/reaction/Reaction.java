package org.bazar.chat.domain.reaction;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.bazar.chat.domain.DomainObject;

/**
 * Jpa сущность Реакция
 */
@Getter
@Setter
@Entity
@Table(name = "reaction")
public class Reaction extends DomainObject {
    /**
     * Код реакции
     */
    @Column(name = "code")
    private String code;

    /**
     * Значение реакции
     */
    @Column(name = "value")
    private String value;

    /**
     * Тип реакции
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ReactionType type;
}
