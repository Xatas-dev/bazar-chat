package org.bazar.chat.adapter.inbound.rest.reaction;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.api.ReactionsApi;
import org.bazar.chat.app.api.reaction.UpdateMessageReactionInbound;
import org.bazar.chat.app.api.reaction.dto.UpdateReactionDto;
import org.bazar.chat.model.ReactionUpdateResponse;
import org.bazar.chat.app.api.reaction.GetMessageReactionListInbound;
import org.bazar.chat.app.api.reaction.dto.MessageReactionListDto;
import org.bazar.chat.model.MessageReactionListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReactionController implements ReactionsApi {
    private final UpdateMessageReactionInbound updateMessageReactionInbound;
    private final RestReactionMapper restReactionMapper;
    private final GetMessageReactionListInbound getMessageReactionListInbound;

    @Override
    public ResponseEntity<MessageReactionListResponse> getReactionsListByMessage(Long chatId, Long messageId) {
        MessageReactionListDto messageReactionList = getMessageReactionListInbound.execute(chatId, messageId);
        return ResponseEntity.ok(restReactionMapper.toMessageReactionListResponse(messageReactionList));
    }

    @Override
    public ResponseEntity<ReactionUpdateResponse> updateMessageReaction(Long chatId, Long messageId, Long reactionId) {
        UpdateReactionDto result = updateMessageReactionInbound.execute(chatId, messageId, reactionId);
        return ResponseEntity.ok().body(new ReactionUpdateResponse(result.messageId(), result.reactionId(), result.count()));
    }
}
