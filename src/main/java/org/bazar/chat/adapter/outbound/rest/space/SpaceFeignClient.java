package org.bazar.chat.adapter.outbound.rest.space;

import org.bazar.chat.adapter.outbound.rest.space.dto.SpaceUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign-client сервиса bazar-space
 */
@FeignClient(name = "bazar-space", url = "${service.bazar-space.url}")
public interface SpaceFeignClient {
    @GetMapping("/spaces/{spaceId}/users")
    SpaceUserResponse getUsersBySpace(@PathVariable Long spaceId);
}
