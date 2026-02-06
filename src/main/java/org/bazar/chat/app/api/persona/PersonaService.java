package org.bazar.chat.app.api.persona;

import org.bazar.chat.app.api.persona.model.UserDto;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для взаимодействия с bazar-persona
 */
public interface PersonaService {
    /**
     * Получить информацию по пользователям по их идентификаторам
     *
     * @param userIds Список идентификаторов пользователей
     * @return Информация по пользователям
     */
    List<UserDto> getUsersByIds(List<UUID> userIds);
}
