package org.bazar.chat.app.api.reaction;

import org.bazar.chat.domain.reaction.Reaction;

import java.util.List;

/**
 * Репозиторий для работы с сущностью реакции
 */
public interface ReactionRepository {
    /**
     * Получить все стандартные реакции
     *
     * @return Список реакций
     */
    List<Reaction> getAllReactions();

    /**
     * Получить прокси-объект только с полем id
     *
     * @param reactionId Идентификатор реакции
     * @return Прокси-объект реакции
     */
    Reaction getReference(Long reactionId);
}
