package org.bazar.chat.app.api.message;

import org.bazar.chat.app.api.message.dto.CreateMessageDto;
import org.bazar.chat.app.api.message.dto.GetMessagePageDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Сервис для работы со сценариями по сущности Сообщение
 */
public interface MessageService {
    /**
     * Получить сообщения чата с пагинацией
     *
     * @param chatId Идентификатор чата
     * @param pageable Информация по пагинации
     * @return Сообщения чата
     */
    GetMessagePageDto getChatMessages(Long chatId, Pageable pageable);

    /**
     * Создать сообщение
     *
     * @param dto DTO для создания сообщения
     */
    void createMessage(CreateMessageDto dto);

    /**
     * Удалить истекшие сообщения
     */
    void deleteExpiredMessages();

    /**
     * Удалить сообщения по идентификатору чата и идентификаторам сообщений
     *
     * @param chatId Идентификатор чата
     * @param messageIds Идентификаторы сообщений
     */
    void deleteMessages(Long chatId, List<Long> messageIds);

    /**
     * Обновить содержимое сообщения
     *
     * @param updateMessageDto DTO для обновления содержимого сообщения
     */
    void updateMessageContent(UpdateMessageDto updateMessageDto);
}
