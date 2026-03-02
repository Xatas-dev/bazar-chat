package org.bazar.chat.adapter.inbound.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bazar.chat.adapter.inbound.kafka.dto.SpaceDeletingDto;
import org.bazar.chat.app.api.chat.DeleteChatBySpaceIdInbound;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

/**
 * Слушатель топика по удалению пространства
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ChatConsumer {
    private final ObjectMapper mapper;
    private final DeleteChatBySpaceIdInbound deleteChatBySpaceIdInbound;

    @KafkaListener(topics = "${kafka.space-delete.topic}")
    public void spaceDelete(String message) {
        log.info("Received message: {} in [space-delete] topic", message);
        SpaceDeletingDto dto = mapper.readValue(message, SpaceDeletingDto.class);
        deleteChatBySpaceIdInbound.execute(dto.spaceId());
    }
}
