package org.bazar.chat.app.service.message;

import org.bazar.chat.app.api.message.dto.AllowedActions;
import org.bazar.chat.domain.message.Message;

import java.util.List;

public interface MessageAllowedActionsResolver {
    List<AllowedActions> getAllowedActions(Message message);
}
