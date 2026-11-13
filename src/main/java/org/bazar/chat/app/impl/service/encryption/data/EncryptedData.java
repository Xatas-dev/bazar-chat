package org.bazar.chat.app.impl.service.encryption.data;

public record EncryptedData(
        byte[] ciphertext,
        byte[] iv
) {
    public EncryptedData {
        ciphertext = ciphertext.clone();
        iv = iv.clone();
    }

    @Override
    public byte[] ciphertext() {
        return ciphertext.clone();
    }

    @Override
    public byte[] iv() {
        return iv.clone();
    }
}
