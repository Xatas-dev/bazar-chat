package org.bazar.chat.app.impl.service.encryption;

import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.domain.message.Message;

import javax.crypto.SecretKey;

public interface ChatEncryptionService {
    void encrypt(Message message, String content);

    String decrypt(Message message, SecretKey dek);

    SecretKey getDek(Chat chat);
}
