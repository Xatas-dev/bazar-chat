package org.bazar.chat.adapter.inbound.rest.reaction;

import io.swagger.v3.oas.annotations.Operation;
import org.bazar.chat.adapter.inbound.rest.reaction.dto.V1MessageReactionListResponse;
import org.bazar.chat.adapter.inbound.rest.reaction.dto.V1ReactionUpdateResponse;
import org.springframework.http.ResponseEntity;

public interface ReactionControllerSwagger {
    @Operation(summary = "Get list of user's reactions by message id", description = "Retrieve a list of reactions and users that put those reactions")
    ResponseEntity<V1MessageReactionListResponse> getMessageReactions(Long spaceId, Long chatId, Long messageId);

    @Operation(summary = "Change reaction state on a message", description = "Изменение состояния реакции на сообщении")
    ResponseEntity<V1ReactionUpdateResponse> updateMessageReaction(Long spaceId, Long chatId, Long messageId, Long reactionId);
}
