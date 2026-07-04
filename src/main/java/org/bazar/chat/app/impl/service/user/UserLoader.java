package org.bazar.chat.app.impl.service.user;

import org.bazar.chat.app.api.persona.model.UserDto;
import org.bazar.chat.domain.message.Message;
import org.bazar.chat.domain.reaction.MessageReaction;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Сервис для загрузки пользователей
 */
public interface UserLoader {
    /**
     * Загрузить пользователей для сообщений
     *
     * @param messages Список сообщений
     * @return Мапа пользователей
     */
    Map<UUID, UserDto> loadUsersForMessages(List<Message> messages);

    /**
     * Загрузить пользователей для реакций
     *
     * @param messageReactions Список реакций на сообщение
     * @return Мапа пользователей
     */
    Map<UUID, UserDto> loadUsersForReactions(List<MessageReaction> messageReactions);

    /**
     * Загрузить пользователя
     *
     * @param userId Идентификатор пользователя
     * @return Пользователь
     */
    Optional<UserDto> getUserById(UUID userId);
}
