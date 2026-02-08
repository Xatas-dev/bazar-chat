package org.bazar.chat.adapter.outbound.rest.persona;

import org.bazar.chat.adapter.outbound.rest.persona.dto.PersonaUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Feign-client сервиса bazar-persona
 */
@FeignClient(name = "bazar-persona", url = "${service.bazar-persona.url}")
public interface PersonaFeignClient {
    @GetMapping(value = "/users")
    List<PersonaUserResponse> getUsers(@RequestParam("ids") List<String> ids);
}
