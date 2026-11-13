package org.bazar.chat.app.impl.service.encryption.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.exception.InternalException;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum MessageEncryptionFormat {

    V1((short) 1) {
        @Override
        public byte[] createAad(Long chatId, Long messageId) {
            return ("chat-message:v1:" + chatId + ":" + messageId).getBytes(StandardCharsets.UTF_8);
        }
    };

    private final short version;

    public abstract byte[] createAad(Long chatId, Long messageId);

    public static MessageEncryptionFormat fromVersion(short version) {
        return Arrays.stream(values())
                .filter(format -> format.version == version)
                .findFirst()
                .orElseThrow(() -> new InternalException("Unsupported message encryption version: " + version));
    }
}