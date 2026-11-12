package org.bazar.chat.domain.chat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.bazar.chat.domain.DomainObject;

/**
 * Jpa сущность Чат
 */
@Getter
@Setter
@Entity
@Table(name = "chat")
public class Chat extends DomainObject {
    /**
     * Идентификатор пространства
     */
    @Column(name = "space_id")
    private Long spaceId;

    /**
     * Ключ шифрования DEK
     */
    @Column(name = "encrypted_dek")
    private byte[] encryptedDek;

    /**
     * Вектор инициализации
     */
    @Column(name = "dek_iv")
    private byte[] dekIv;

    /**
     * Версия ключа шифрования KEK
     */
    @Column(name = "kek_version")
    private String kekVersion;
}
