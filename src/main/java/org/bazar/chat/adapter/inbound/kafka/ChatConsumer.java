package org.bazar.chat.adapter.inbound.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bazar.chat.adapter.inbound.kafka.dto.SpaceDeletingDto;
import org.bazar.chat.app.api.chat.ChatService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatConsumer {
    private final ObjectMapper mapper;
    private final ChatService chatService;

    @KafkaListener(topics = "${kafka.space-delete.topic}")
    public void spaceDelete(String message) {
        log.info("Received message: {} in [space-delete] topic", message);
        SpaceDeletingDto dto = mapper.readValue(message, SpaceDeletingDto.class);
        chatService.deleteChatBySpaceId(dto.spaceId());
    }
}
