package org.bazar.chat.app.api.reaction;

import org.bazar.chat.app.api.reaction.dto.UpdateReactionDto;

/**
 * Входной интерфейс для изменения реакции на сообщение
 */
public interface UpdateMessageReactionInbound {
    /**
     * Обновить реакцию на сообщение
     *
     * @param chatId Идентификатор чата
     * @param messageId Идентификатор сообщения
     * @param reactionId Идентификатор реакции
     * @return DTO измененной реакции
     */
    UpdateReactionDto execute(Long chatId, Long messageId, Long reactionId);
}
