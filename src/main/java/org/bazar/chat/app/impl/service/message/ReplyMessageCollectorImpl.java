package org.bazar.chat.app.impl.service.message;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.SettingProperties;
import org.bazar.chat.app.api.message.dto.AuthorStatus;
import org.bazar.chat.app.api.message.dto.ReplyMessageDto;
import org.bazar.chat.app.api.persona.model.UserDto;
import org.bazar.chat.app.impl.message.MessageMapper;
import org.bazar.chat.app.impl.service.encryption.ChatEncryptionService;
import org.bazar.chat.domain.message.Message;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReplyMessageCollectorImpl implements ReplyMessageCollector {
    private final MessageMapper messageMapper;
    private final SettingProperties settingProperties;
    private final ChatEncryptionService chatEncryptionService;

    @Override
    public ReplyMessageDto getReplyMessageDto(Message message, Map<UUID, UserDto> usersMap, SecretKey chatDek) {
        Message replyMessage = message.getReplyMessage();
        if (replyMessage == null || !replyMessage.getVisible()) {
            return null;
        }

        UserDto user = usersMap.get(replyMessage.getUserId());
        AuthorStatus authorStatus = AuthorStatus.from(user);

        String replyContent = chatEncryptionService.decrypt(replyMessage, chatDek);
        String contentPreview = replyContent.substring(0, Math.min(replyContent.length(), settingProperties.getMessage().getReplyPreviewLength()));
        return messageMapper.toReplyMessageDto(replyMessage, user, authorStatus, contentPreview);
    }
}
