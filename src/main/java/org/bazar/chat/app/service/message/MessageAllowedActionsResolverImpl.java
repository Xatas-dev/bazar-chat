package org.bazar.chat.app.service.message;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.message.dto.AllowedActions;
import org.bazar.chat.app.service.AuthorizationService;
import org.bazar.chat.domain.message.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageAllowedActionsResolverImpl implements MessageAllowedActionsResolver {
    private final AuthorizationService authorizationService;

    @Override
    public List<AllowedActions> getAllowedActions(Message message) {
        if (authorizationService.isMessageBelongsToCurrentUser(message)) {
            return List.of(AllowedActions.DELETE, AllowedActions.EDIT);
        }
        return List.of();
    }
}
