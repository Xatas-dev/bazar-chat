package org.bazar.chat.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.bazar.chat.kafka.dto.SpaceDeletingDto
import org.bazar.chat.service.ChatService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class SpaceConsumer(
    private val objectsMapper: ObjectMapper = jacksonObjectMapper(),
    private val chatService: ChatService
) {
    @KafkaListener(topics = [$$"${kafka.space-delete.topic}"], groupId = $$"${kafka.space-delete.group-id}")
    fun listen(message: String) {
        val spaceDeletingDto = objectsMapper.readValue(message, SpaceDeletingDto::class.java)
        println("[space-delete topic] Received message: $spaceDeletingDto")
        chatService.deleteChatBySpaceId(spaceDeletingDto.spaceId)
    }
}