package org.bazar.chat.domain.chat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.bazar.chat.domain.DomainObject;

@Getter
@Setter
@Entity
@Table(name = "chat")
public class Chat extends DomainObject {
    @Column(name = "spaceId")
    private Long spaceId;
}
