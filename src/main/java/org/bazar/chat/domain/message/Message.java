package org.bazar.chat.domain.message;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.bazar.chat.domain.DomainObject;
import org.bazar.chat.domain.chat.Chat;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "message")
public class Message extends DomainObject {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @Column(name = "content")
    private String content;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "visible")
    private Boolean visible = true;
}
