package org.bazar.chat.app.api.reaction;

import org.bazar.chat.app.api.reaction.dto.GetReactionDto;

import java.util.List;

/**
 * Входной интерфейс получения реакций чата
 */
public interface GetChatReactionsInbound {
    /**
     * Получить список реакций чата
     *
     * @return Список реакций чата
     */
    List<GetReactionDto> execute();
}
