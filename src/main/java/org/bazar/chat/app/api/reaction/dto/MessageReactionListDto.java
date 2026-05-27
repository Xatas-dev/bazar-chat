package org.bazar.chat.app.api.reaction.dto;

import java.util.List;

/**
 * DTO получения списка реакций и пользователей которые их оставили
 *
 * @param reactions Реакции на сообщения и пользователи
 */
public record MessageReactionListDto(
        List<MessageReactionDto> reactions
) {
}
