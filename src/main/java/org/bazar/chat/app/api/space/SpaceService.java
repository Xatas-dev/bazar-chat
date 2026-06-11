package org.bazar.chat.app.api.space;

import org.bazar.chat.app.api.space.dto.SpaceUserDto;

import java.util.Set;

/**
 * Сервис для взаимодействия с bazar-space
 */
public interface SpaceService {
    /**
     * Получить всех пользователей в пространстве
     *
     * @param spaceId Идентификатор пространства
     * @return Список пользователей
     */
    Set<SpaceUserDto> getUsersBySpaceId(Long spaceId);
}
