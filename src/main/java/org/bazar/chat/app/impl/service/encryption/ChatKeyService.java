package org.bazar.chat.app.impl.service.encryption;

import org.bazar.chat.app.impl.service.encryption.data.WrappedDek;

import javax.crypto.SecretKey;


public interface ChatKeyService {
    WrappedDek createDek(Long chatId);

    SecretKey decryptDek(Long chatId, byte[] encryptedDek, byte[] iv, String kekVersion);
}
