package org.bazar.chat.adapter.outbound.websocket.dto.payload;

import java.util.List;

public record MessageDeletedPayload(
        List<Long> ids
) {
}
