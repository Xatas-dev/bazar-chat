package org.bazar.chat.app.api.message;

import org.bazar.chat.app.api.message.dto.CreateMessageDto;
import org.bazar.chat.app.api.message.dto.GetMessagePageDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageService {
    GetMessagePageDto getChatMessages(Long chatId, Pageable pageable);

    void createMessage(CreateMessageDto dto);

    void deleteExpiredMessages();

    void deleteMessages(Long chatId, List<Long> messageIds);
}
