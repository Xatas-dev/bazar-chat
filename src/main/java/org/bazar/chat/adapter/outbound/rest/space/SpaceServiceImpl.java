package org.bazar.chat.adapter.outbound.rest.space;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.space.SpaceService;
import org.bazar.chat.app.api.space.dto.SpaceUserDto;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Реализация сервиса для взаимодействия с bazar-space
 */
@Component
@RequiredArgsConstructor
public class SpaceServiceImpl implements SpaceService {
    private final SpaceFeignClient spaceFeignClient;
    private final SpaceMapper spaceMapper;

    @Override
    public Set<SpaceUserDto> getUsersBySpaceId(Long spaceId) {
        return spaceMapper.toSpaceUserDtoSet(spaceFeignClient.getUsersBySpace(spaceId).users());
    }
}
