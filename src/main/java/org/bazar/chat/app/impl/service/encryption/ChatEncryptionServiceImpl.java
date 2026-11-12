package org.bazar.chat.app.impl.service.encryption;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.SettingProperties;
import org.bazar.chat.app.impl.service.encryption.data.EncryptedData;
import org.bazar.chat.app.impl.service.encryption.data.MessageEncryptionFormat;
import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.domain.message.Message;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class ChatEncryptionServiceImpl implements ChatEncryptionService {
    private final AesGcmEncryptionService aesGcmEncryptionService;
    private final SettingProperties properties;
    private final ChatKeyService chatKeyService;

    @PostConstruct
    void initialize() {
        MessageEncryptionFormat.fromVersion(properties.getEncryption().getMessage().getCurrentVersion());
    }

    @Override
    public void encrypt(Message message, String content) {
        if (content == null) {
            throw new IllegalArgumentException("Message content cannot be null");
        }

        Chat chat = message.getChat();
        SecretKey dek = getDek(chat);
        MessageEncryptionFormat format = MessageEncryptionFormat.fromVersion(
                properties.getEncryption().getMessage().getCurrentVersion());

        byte[] aad = format.createAad(chat.getId(), message.getId());
        EncryptedData encryptedData = aesGcmEncryptionService.encrypt(content.getBytes(StandardCharsets.UTF_8), dek, aad);
        message.setEncryptedContent(encryptedData.ciphertext());
        message.setIv(encryptedData.iv());
        message.setEncryptionVersion(format.getVersion());
    }

    @Override
    public String decrypt(Message message, SecretKey dek) {
        Chat chat = message.getChat();
        MessageEncryptionFormat format = MessageEncryptionFormat.fromVersion(message.getEncryptionVersion());
        byte[] aad = format.createAad(chat.getId(), message.getId());
        byte[] content = aesGcmEncryptionService.decrypt(
                new EncryptedData(message.getEncryptedContent(), message.getIv()),
                dek,
                aad
        );

        return new String(content, StandardCharsets.UTF_8);
    }

    @Override
    public SecretKey getDek(Chat chat) {
        return chatKeyService.decryptDek(chat.getId(), chat.getEncryptedDek(), chat.getDekIv(), chat.getKekVersion());
    }
}
