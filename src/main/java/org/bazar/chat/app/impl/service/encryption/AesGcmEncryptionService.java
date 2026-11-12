package org.bazar.chat.app.impl.service.encryption;

import org.bazar.chat.app.api.exception.InternalException;
import org.bazar.chat.app.impl.service.encryption.data.EncryptedData;
import org.springframework.stereotype.Component;

import javax.crypto.AEADBadTagException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

@Component
public class AesGcmEncryptionService {
    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int KEY_LENGTH_BITS = 256;
    private static final int IV_LENGTH_BYTES = 12;
    private static final int TAG_LENGTH_BITS = 128;

    private final SecureRandom secureRandom = new SecureRandom();

    public EncryptedData encrypt(byte[] plaintext, SecretKey key, byte[] aad) {
        validateKey(key);

        byte[] iv = generateIv();

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH_BITS, iv));
            cipher.updateAAD(aad);

            byte[] ciphertext = cipher.doFinal(plaintext);

            return new EncryptedData(ciphertext, iv);
        } catch (GeneralSecurityException e) {
            throw new InternalException("Failed to encrypt data", e);
        }
    }

    public byte[] decrypt(EncryptedData encryptedData, SecretKey key, byte[] aad) {
        validateKey(key);

        byte[] iv = encryptedData.iv();

        if (iv.length != IV_LENGTH_BYTES) {
            throw new InternalException("Invalid IV length");
        }

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH_BITS, iv));
            cipher.updateAAD(aad);

            return cipher.doFinal(encryptedData.ciphertext());
        } catch (AEADBadTagException e) {
            throw new InternalException("Data authentication failed: invalid key, IV, AAD or ciphertext", e);
        } catch (GeneralSecurityException e) {
            throw new InternalException("Failed to decrypt data", e);
        }
    }

    public SecretKey generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(KEY_LENGTH_BITS, secureRandom);
            return keyGenerator.generateKey();
        } catch (GeneralSecurityException e) {
            throw new InternalException("Failed to generate encryption key", e);
        }
    }

    // =================================================================================================================
    // = Implementation
    // =================================================================================================================

    private byte[] generateIv() {
        byte[] iv = new byte[IV_LENGTH_BYTES];
        secureRandom.nextBytes(iv);
        return iv;
    }

    private void validateKey(SecretKey key) {
        if (key == null
                || !"AES".equalsIgnoreCase(key.getAlgorithm())
                || key.getEncoded() == null
                || key.getEncoded().length != KEY_LENGTH_BITS / 8) {
            throw new InternalException("AES-256 key is required");
        }
    }
}
