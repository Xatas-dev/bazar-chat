package org.bazar.chat.app.service.message;

import org.bazar.chat.app.api.persona.model.UserDto;
import org.bazar.chat.domain.message.Message;

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
    Map<UUID, UserDto> loadUsers(List<Message> messages);

    /**
     * Загрузить пользователя
     *
     * @param userId Идентификатор пользователя
     * @return Пользователь
     */
    Optional<UserDto> getUserById(UUID userId);
}
