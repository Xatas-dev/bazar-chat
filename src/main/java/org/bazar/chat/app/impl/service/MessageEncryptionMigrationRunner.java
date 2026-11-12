package org.bazar.chat.app.impl.service;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.chat.ChatRepository;
import org.bazar.chat.app.api.message.MessageRepository;
import org.bazar.chat.app.impl.service.encryption.ChatEncryptionService;
import org.bazar.chat.app.impl.service.encryption.ChatKeyService;
import org.bazar.chat.app.impl.service.encryption.data.WrappedDek;
import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.domain.message.Message;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// TODO: к удалению после миграции
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "settings.feature-toggle.encryption-migration-enabled", havingValue = "true")
public class MessageEncryptionMigrationRunner implements ApplicationRunner {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final ChatKeyService chatKeyService;
    private final ChatEncryptionService chatEncryptionService;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        List<Chat> chatsForMigration = chatRepository.findChatsForMigration();

        for (Chat chat : chatsForMigration) {
            migrateChat(chat);
        }
    }

    private void migrateChat(Chat chat) {
        WrappedDek dek = chatKeyService.createDek(chat.getId());
        chat.setEncryptedDek(dek.encryptedDek());
        chat.setDekIv(dek.iv());
        chat.setKekVersion(dek.kekVersion());
        chatRepository.saveAndFlush(chat);

        List<Message> messages = messageRepository.findByChatId(chat.getId());
        for (Message message : messages) {
            encryptMessage(message);
        }
    }

    private void encryptMessage(Message message) {
        String content = message.getContent();

        chatEncryptionService.encrypt(message, content);

        message.setContent(null);
        messageRepository.saveAndFlush(message);
    }
}
