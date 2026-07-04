package org.bazar.chat.app.impl.service.message;

import org.bazar.chat.app.api.message.dto.ReplyMessageDto;
import org.bazar.chat.app.api.persona.model.UserDto;
import org.bazar.chat.domain.message.Message;

import java.util.Map;
import java.util.UUID;

/**
 * Интерфейс для сборки информации по сообщению, на которое было отправлено ответное сообщение
 */
public interface ReplyMessageCollector {
    /**
     * Собрать информацию по сообщению, на которое было отправлено ответное сообщение
     *
     * @param message Сообщение, на которое было отправлено ответное сообщение
     * @param usersMap Карта пользователей, необходимая для получения информации об авторе ответного сообщения
     * @return DTO с информацией по сообщению, на которое было отправлено ответное сообщение
     */
    ReplyMessageDto getReplyMessageDto(Message message, Map<UUID, UserDto> usersMap);
}
