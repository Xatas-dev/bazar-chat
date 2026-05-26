package org.bazar.chat.app.api.reaction;

import org.bazar.chat.domain.reaction.MessageReaction;

import java.util.UUID;

/**
 * Репозиторий для работы с сущностью реакции на сообщение
 */
public interface MessageReactionRepository {
    /**
     * Проверить, есть ли у пользователя определенная реакция на сообщении
     *
     * @param messageId Идентификатор сообщения
     * @param reactionId Идентификатор реакции
     * @param userId Идентификатор пользователя
     * @return Результат проверки
     */
    boolean existsUserMessageReaction(Long messageId, Long reactionId, UUID userId);

    /**
     * Удалить реакцию пользователя на сообщение
     *
     * @param messageId Идентификатор сообщения
     * @param reactionId Идентификатор реакции
     * @param userId Идентификатор пользователя
     */
    void deleteUserMessageReaction(Long messageId, Long reactionId, UUID userId);

    /**
     * Получить количество реакций пользователя на сообщение
     *
     * @param messageId Идентификатор сообщения
     * @param userId Идентификатор пользователя
     * @return Количество реакций пользователя на сообщение
     */
    long countUserMessageReactions(Long messageId, UUID userId);

    /**
     * Получить количество определенных реакций на сообщение
     *
     * @param messageId Идентификатор сообщения
     * @param reactionId Идентификатор реакции
     * @return Количество определенных реакций на сообщение
     */
    long countMessageReactions(Long messageId, Long reactionId);

    /**
     * Сохранить рекцию на сообщение
     *
     * @param messageReaction Реакция на сообщение
     */
    void save(MessageReaction messageReaction);
}
