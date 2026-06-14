package org.bazar.chat.adapter.inbound.rest.reaction;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.adapter.inbound.rest.reaction.dto.V1MessageReactionListResponse;
import org.bazar.chat.adapter.inbound.rest.reaction.dto.V1ReactionUpdateResponse;
import org.bazar.chat.app.api.reaction.GetMessageReactionListInbound;
import org.bazar.chat.app.api.reaction.UpdateMessageReactionInbound;
import org.bazar.chat.app.api.reaction.dto.MessageReactionListDto;
import org.bazar.chat.app.api.reaction.dto.UpdateReactionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/spaces/{spaceId}/chats/{chatId}/messages/{messageId}/reactions")
public class ReactionController implements ReactionControllerSwagger {
    private final UpdateMessageReactionInbound updateMessageReactionInbound;
    private final RestReactionMapper restReactionMapper;
    private final GetMessageReactionListInbound getMessageReactionListInbound;

    @GetMapping("/users")
    public ResponseEntity<V1MessageReactionListResponse> getMessageReactions(@PathVariable Long spaceId,
                                                                             @PathVariable Long chatId,
                                                                             @PathVariable Long messageId) {
        MessageReactionListDto messageReactionList = getMessageReactionListInbound.execute(chatId, messageId);
        return ResponseEntity.ok(restReactionMapper.toV1MessageReactionListResponse(messageReactionList));
    }

    @PutMapping("/{reactionId}")
    public ResponseEntity<V1ReactionUpdateResponse> updateMessageReaction(@PathVariable Long spaceId,
                                                                          @PathVariable Long chatId,
                                                                          @PathVariable Long messageId,
                                                                          @PathVariable Long reactionId) {
        UpdateReactionDto result = updateMessageReactionInbound.execute(chatId, messageId, reactionId);
        return ResponseEntity.ok(restReactionMapper.toV1ReactionUpdateResponse(result));
    }
}
