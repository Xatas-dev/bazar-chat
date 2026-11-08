package org.bazar.chat.app.impl.service.encryption;

import org.bazar.chat.app.impl.service.encryption.data.EncryptedMessage;
import org.bazar.chat.domain.message.Message;

public interface ChatEncryptionService {
    EncryptedMessage encrypt(Message message, String content);

    String decrypt(Message message, EncryptedMessage encryptedMessage);
}
