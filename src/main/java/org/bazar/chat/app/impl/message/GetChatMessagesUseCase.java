package org.bazar.chat.app.impl.message;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.message.GetChatMessagesInbound;
import org.bazar.chat.app.api.message.MessageRepository;
import org.bazar.chat.app.api.message.dto.AuthorStatus;
import org.bazar.chat.app.api.message.dto.GetMessageDto;
import org.bazar.chat.app.api.message.dto.GetMessagePageDto;
import org.bazar.chat.app.api.message.dto.ReplyMessageDto;
import org.bazar.chat.app.api.persona.model.UserDto;
import org.bazar.chat.app.impl.mapper.PageDtoMapper;
import org.bazar.chat.app.service.message.MessageAllowedActionsResolver;
import org.bazar.chat.app.service.message.ReplyMessageCollector;
import org.bazar.chat.app.service.message.UserLoader;
import org.bazar.chat.domain.message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * Реализация интерфейса для получения сообщений в чате
 */
@Component
@RequiredArgsConstructor
public class GetChatMessagesUseCase implements GetChatMessagesInbound {
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final PageDtoMapper pageDtoMapper;
    private final UserLoader userLoader;
    private final ReplyMessageCollector replyMessageCollector;
    private final MessageAllowedActionsResolver messageAllowedActionsResolver;

    @Override
    public GetMessagePageDto execute(Long chatId, Pageable pageable) {
        Page<Message> messages = messageRepository.findAllVisibleByChatId(chatId, pageable);
        Map<UUID, UserDto> usersMap = userLoader.loadUsers(messages.getContent());
        Page<GetMessageDto> dtoPage = messages.map(message -> {
                    UserDto user = usersMap.get(message.getUserId());
                    AuthorStatus authorStatus = AuthorStatus.from(user);
                    ReplyMessageDto reply = replyMessageCollector.getReplyMessageDto(message, usersMap);

                    return messageMapper.toGetMessageDto(
                            message,
                            messageAllowedActionsResolver.getAllowedActions(message),
                            user,
                            authorStatus,
                            reply
                    );
                }
        );
        return pageDtoMapper.toGetMessagePageDto(dtoPage);
    }
}
