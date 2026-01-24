package org.bazar.chat.it.testutil;

import builder.ChatBuilder;
import builder.MessageBuilder;
import lombok.RequiredArgsConstructor;
import org.bazar.chat.adapter.outbound.persistence.chat.ChatJpaRepository;
import org.bazar.chat.adapter.outbound.persistence.message.MessageJpaRepository;
import org.bazar.chat.domain.chat.Chat;
import org.bazar.chat.domain.message.Message;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TestDataHelper {
    private final ChatJpaRepository chatJpaRepository;
    private final MessageJpaRepository messageJpaRepository;

    public Chat createChatWith(Long spaceId) {
        return chatJpaRepository.save(ChatBuilder.buildWith(spaceId));
    }

    public Message createMessageWith(Chat chat, String content, UUID userId) {
        return messageJpaRepository.save(MessageBuilder.buildWith(chat, content, userId));
    }

    public void clearTables() {
        messageJpaRepository.deleteAll();
        chatJpaRepository.deleteAll();
    }
}
