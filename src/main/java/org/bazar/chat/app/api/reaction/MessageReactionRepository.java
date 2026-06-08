package org.bazar.chat.app.api.reaction;

import org.bazar.chat.domain.reaction.MessageReaction;

import java.util.List;
import java.util.UUID;

/**
 * Репозиторий для работы с сущностью реакция на сообщение
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
     * Сохранить рекцию на сообщение
     *
     * @param messageReaction Реакция на сообщение
     */
    void save(MessageReaction messageReaction);

    /**
     * Найти реакции на сообщения по идентификатору сообщения
     *
     * @param messageId Идентификатор сообщения
     * @return Список найденных реакций
     */
    List<MessageReaction> findAllByMessageId(Long messageId);

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
     * Удалить самую старую реакцию на сообщение
     *
     * @param messageId Идентификатор сообщения
     * @param userId Идентификатор пользователя
     */
    void deleteOldestUserMessageReaction(Long messageId, UUID userId);
}
