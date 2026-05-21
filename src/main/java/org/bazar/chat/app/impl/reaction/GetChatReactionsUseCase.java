package org.bazar.chat.app.impl.reaction;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.reaction.GetChatReactionsInbound;
import org.bazar.chat.app.api.reaction.ReactionRepository;
import org.bazar.chat.app.api.reaction.dto.GetReactionDto;
import org.bazar.chat.domain.reaction.Reaction;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Реализация интерфейса получения реакций чата
 */
@Component
@RequiredArgsConstructor
public class GetChatReactionsUseCase implements GetChatReactionsInbound {
    private final ReactionRepository reactionRepository;
    private final ReactionMapper reactionMapper;

    @Override
    public List<GetReactionDto> execute() {
        List<Reaction> reactions = reactionRepository.getAllReactions();
        return reactionMapper.toGetReactionDtoList(reactions);
    }
}
