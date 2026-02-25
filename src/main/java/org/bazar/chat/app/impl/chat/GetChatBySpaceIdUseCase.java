package org.bazar.chat.app.impl.chat;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.chat.ChatRepository;
import org.bazar.chat.app.api.chat.GetChatBySpaceIdInbound;
import org.bazar.chat.app.api.chat.dto.GetChatDto;
import org.bazar.chat.app.api.exception.BusinessException;
import org.bazar.chat.app.api.exception.ErrorCode;
import org.springframework.stereotype.Component;

/**
 * Реализация интерфейса для получения информации по чату по идентификатору пространства
 */
@Component
@RequiredArgsConstructor
public class GetChatBySpaceIdUseCase implements GetChatBySpaceIdInbound {
    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;

    @Override
    public GetChatDto execute(Long spaceId) {
        return chatMapper.mapToGetChatDto(chatRepository.findBySpaceId(spaceId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CHAT_BY_SPACE_NOT_FOUND, spaceId)));
    }
}
