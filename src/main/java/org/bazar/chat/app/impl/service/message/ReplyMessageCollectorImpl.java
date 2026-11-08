package org.bazar.chat.app.impl.service.message;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.SettingProperties;
import org.bazar.chat.app.api.message.dto.AuthorStatus;
import org.bazar.chat.app.api.message.dto.ReplyMessageDto;
import org.bazar.chat.app.api.persona.model.UserDto;
import org.bazar.chat.app.impl.message.MessageMapper;
import org.bazar.chat.app.impl.service.encryption.ChatEncryptionService;
import org.bazar.chat.app.impl.service.encryption.data.EncryptedMessage;
import org.bazar.chat.domain.message.Message;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReplyMessageCollectorImpl implements ReplyMessageCollector {
    private final MessageMapper messageMapper;
    private final SettingProperties settingProperties;
    private final ChatEncryptionService chatEncryptionService;

    @Override
    public ReplyMessageDto getReplyMessageDto(Message message, Map<UUID, UserDto> usersMap) {
        Message replyMessage = message.getReplyMessage();
        if (replyMessage == null || !replyMessage.getVisible()) {
            return null;
        }

        UserDto user = usersMap.get(replyMessage.getUserId());
        AuthorStatus authorStatus = AuthorStatus.from(user);

        String replyContent = chatEncryptionService.decrypt(
                replyMessage,
                new EncryptedMessage(message.getEncryptedContent(), message.getIv(), message.getEncryptionVersion()));
        String contentPreview = replyContent.substring(0, Math.min(replyMessage.getContent().length(), settingProperties.getMessage().getReplyPreviewLength()));
        return messageMapper.toReplyMessageDto(replyMessage, user, authorStatus, contentPreview);
    }
}
