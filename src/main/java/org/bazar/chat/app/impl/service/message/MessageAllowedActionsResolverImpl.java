package org.bazar.chat.app.impl.service.message;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.message.dto.AllowedActions;
import org.bazar.chat.app.api.auth.AuthenticationService;
import org.bazar.chat.domain.message.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageAllowedActionsResolverImpl implements MessageAllowedActionsResolver {
    private final AuthenticationService authenticationService;

    @Override
    public List<AllowedActions> getAllowedActions(Message message) {
        if (authenticationService.isMessageBelongsToCurrentUser(message)) {
            return List.of(AllowedActions.DELETE, AllowedActions.EDIT);
        }
        return List.of();
    }
}
