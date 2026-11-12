package org.bazar.chat.app.impl.service.encryption;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.impl.service.encryption.data.EncryptedData;
import org.bazar.chat.app.impl.service.encryption.data.WrappedDek;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class ChatKeyServiceImpl implements ChatKeyService {
    private final AesGcmEncryptionService encryptionService;
    private final KekProvider kekProvider;

    public WrappedDek createDek(Long chatId) {
        SecretKey dek = encryptionService.generateKey();

        String kekVersion = kekProvider.getCurrentVersion();
        SecretKey kek = kekProvider.getCurrentKey();

        EncryptedData encryptedDek = encryptionService.encrypt(dek.getEncoded(), kek, dekAad(chatId));

        return new WrappedDek(encryptedDek.ciphertext(), encryptedDek.iv(), kekVersion);
    }

    public SecretKey decryptDek(Long chatId, byte[] encryptedDek, byte[] iv, String kekVersion) {
        SecretKey kek = kekProvider.getKey(kekVersion);
        byte[] dekBytes = encryptionService.decrypt(new EncryptedData(encryptedDek, iv), kek, dekAad(chatId));
        return new SecretKeySpec(dekBytes, "AES");
    }

    // =================================================================================================================
    // = Implementation
    // =================================================================================================================

    private byte[] dekAad(Long chatId) {
        return ("chat-dek:" + kekProvider.getCurrentKey() + ":" + chatId).getBytes(StandardCharsets.UTF_8);
    }
}
