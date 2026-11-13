package org.bazar.chat.app.impl.service.encryption.data;

public record WrappedDek(
        byte[] encryptedDek,
        byte[] iv,
        String kekVersion
) {
}
