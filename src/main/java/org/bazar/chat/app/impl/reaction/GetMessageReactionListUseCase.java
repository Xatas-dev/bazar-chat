package org.bazar.chat.app.impl.reaction;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.exception.BusinessException;
import org.bazar.chat.app.api.exception.ErrorCode;
import org.bazar.chat.app.api.message.MessageRepository;
import org.bazar.chat.app.api.persona.model.UserDto;
import org.bazar.chat.app.api.reaction.GetMessageReactionListInbound;
import org.bazar.chat.app.api.reaction.MessageReactionRepository;
import org.bazar.chat.app.api.reaction.dto.MessageReactionDto;
import org.bazar.chat.app.api.reaction.dto.MessageReactionListDto;
import org.bazar.chat.app.api.reaction.dto.ReactionUserDto;
import org.bazar.chat.app.api.reaction.dto.UserStatus;
import org.bazar.chat.app.service.message.UserLoader;
import org.bazar.chat.domain.reaction.MessageReaction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Реализация интерфейса для получения реакций и пользователей поставивших соответствующие реакции
 */
@Component
@RequiredArgsConstructor
class GetMessageReactionListUseCase implements GetMessageReactionListInbound {
    private final MessageRepository messageRepository;
    private final MessageReactionRepository messageReactionRepository;
    private final UserLoader userLoader;
    private final ReactionMapper reactionMapper;

    @Override
    public MessageReactionListDto execute(Long chatId, Long messageId) {
        if (!messageRepository.existsById(messageId)) {
            throw new BusinessException(ErrorCode.MESSAGE_NOT_FOUND, messageId);
        }

        List<MessageReaction> messageReactions = messageReactionRepository.findAllByMessageId(messageId);
        Map<UUID, UserDto> usersMap = userLoader.loadUsersForReactions(messageReactions);

        List<MessageReactionDto> reactions = messageReactions
                .stream()
                .collect(Collectors.groupingBy(mr -> mr.getReaction().getId()))
                .entrySet()
                .stream()
                .map(entry -> {
                    List<ReactionUserDto> users = entry.getValue()
                            .stream()
                            .map(
                                    mr -> {
                                        UserDto userDto = usersMap.get(mr.getUserId());
                                        UserStatus status = UserStatus.from(userDto);
                                        if (userDto == null) {
                                            userDto = new UserDto(mr.getUserId(), null, null);
                                        }
                                        return reactionMapper.toReactionUserDto(userDto, status);
                                    }
                            )
                            .toList();

                    return new MessageReactionDto(entry.getKey(), users);
                })
                .toList();

        return new MessageReactionListDto(reactions);
    }
}
