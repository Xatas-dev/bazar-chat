package org.bazar.chat.app.api.reaction;

import org.bazar.chat.app.api.reaction.dto.MessageReactionListDto;

/**
 * Входной интерфейс по получения списка реакций
 */
public interface GetMessageReactionListInbound {
    /**
     * Получить список реакций с пользователями
     *
     * @param chatId Идентификатор чата
     * @param messageId Идентификатор сообщения
     * @return Список реакций и пользователей
     */
    MessageReactionListDto execute(Long chatId, Long messageId);
}
