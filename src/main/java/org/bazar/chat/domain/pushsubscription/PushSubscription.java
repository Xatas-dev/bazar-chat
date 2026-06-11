package org.bazar.chat.domain.pushsubscription;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.bazar.chat.domain.DomainObject;

import java.util.UUID;

/**
 * Jpa сущность Подписка на push-уведомления
 */
@Getter
@Setter
@Entity
@Table(name = "push_subscription")
public class PushSubscription extends DomainObject {
    /**
     * Идентификатор пользователя
     */
    @Column(name = "userId")
    private UUID userId;

    /**
     * Эндпоинт, по которому отправляется уведомление
     */
    @Column(name = "endpoint")
    private String endpoint;

    /**
     * Открытый ключ подписки
     */
    @Column(name = "p256dh")
    private String p256dh;

    /**
     * Приватный ключ подписки
     */
    @Column(name = "auth")
    private String auth;
}
