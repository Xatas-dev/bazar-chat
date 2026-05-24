package org.bazar.chat.app.service.message;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.persona.PersonaService;
import org.bazar.chat.app.api.persona.model.UserDto;
import org.bazar.chat.domain.message.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class UserLoaderImpl implements UserLoader {
    private final PersonaService personaService;

    @Override
    public Map<UUID, UserDto> loadUsers(List<Message> messages) {
        List<UUID> userIds = Stream.concat(
                        messages.stream().map(Message::getUserId),
                        messages.stream().flatMap(m -> Stream.ofNullable(m.getReplyMessage())).map(Message::getUserId)
                )
                .distinct()
                .toList();
        List<UserDto> usersByIds = personaService.getUsersByIds(userIds);

        return usersByIds.stream()
                .collect(Collectors.toMap(UserDto::userId, Function.identity()));
    }

    @Override
    public Optional<UserDto> getUserById(UUID userId) {
        return personaService.getUsersByIds(List.of(userId)).stream()
                .findFirst();
    }
}
