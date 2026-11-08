package org.bazar.chat.app.impl.service.encryption.data;

public record EncryptedMessage(
        byte[] ciphertext,
        byte[] iv,
        short encryptionVersion
) {

    public EncryptedMessage {
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

    public EncryptedData toEncryptedData() {
        return new EncryptedData(ciphertext, iv);
    }
}
