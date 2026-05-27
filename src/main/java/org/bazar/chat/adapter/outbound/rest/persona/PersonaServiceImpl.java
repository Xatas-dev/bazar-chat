package org.bazar.chat.adapter.outbound.rest.persona;


import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bazar.chat.adapter.outbound.rest.persona.dto.PersonaUserResponse;
import org.bazar.chat.app.api.persona.PersonaService;
import org.bazar.chat.app.api.persona.model.UserDto;
import org.bazar.chat.fw.CaffeineCacheConfig;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Сервис для работы с микросервисом bazar-persona
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PersonaServiceImpl implements PersonaService {
    private final PersonaFeignClient feignClient;
    private final PersonaMapper mapper;
    private final CacheManager cacheManager;

    @Override
    public List<UserDto> getUsersByIds(List<UUID> userIds) {
        return getUsersUsingCache(userIds);
    }

    // =================================================================================================================
    // = Implementation
    // =================================================================================================================

    private List<UserDto> getUsersUsingCache(List<UUID> userIds) {
        Cache cache = Objects.requireNonNull(cacheManager.getCache(CaffeineCacheConfig.PERSONA_USER_CACHE));

        Map<UUID, UserDto> result = new HashMap<>();
        List<UUID> missingUserIds = new ArrayList<>();
        collectCachedAndMissingUsers(cache, userIds, result, missingUserIds);

        List<PersonaUserResponse> missingUsers = getUsersFromFeignClient(missingUserIds);
        processMissingUsers(missingUsers, result, cache);

        return userIds.stream()
                .map(result::get)
                .filter(Objects::nonNull)
                .toList();
    }

    private void collectCachedAndMissingUsers(Cache cache, List<UUID> userIds, Map<UUID, UserDto> result, List<UUID> missingUserIds) {
        userIds.forEach(userId -> {
            UserDto userDto = cache.get(userId, UserDto.class);
            if (userDto != null) {
                result.put(userId, userDto);
            } else {
                missingUserIds.add(userId);
            }
        });
    }

    private void processMissingUsers(List<PersonaUserResponse> missingUsers, Map<UUID, UserDto> result, Cache cache) {
        missingUsers.stream()
                .map(mapper::mapToUserDto)
                .forEach(user -> {
                    result.put(user.userId(), user);
                    cache.put(user.userId(), user);
                });
    }

    private List<PersonaUserResponse> getUsersFromFeignClient(List<UUID> userIds) {
        if (userIds.isEmpty()) {
            return List.of();
        }
        try {
            return feignClient.getUsers(userIds.stream().map(UUID::toString).toList());
        } catch (FeignException e) {
            log.error("Failed to fetch users from bazar-persona, userIds: {}", userIds, e);
            return List.of();
        }
    }
}
