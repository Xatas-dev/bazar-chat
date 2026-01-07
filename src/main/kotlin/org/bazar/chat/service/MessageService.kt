package org.bazar.chat.service

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import org.bazar.chat.model.CreateMessageRequest
import org.bazar.chat.persistence.entity.Chat
import org.bazar.chat.persistence.entity.Message
import org.bazar.chat.persistence.repository.MessageRepository
import org.bazar.chat.utils.extensions.getUserIdFromSecurityContext
import org.bazar.chat.utils.extensions.toMessagePageResponse
import org.bazar.chat.utils.extensions.toMessageResponse
import org.springframework.data.domain.Pageable
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import kotlin.math.log

@Service
@Transactional
class MessageService(
    private val messageRepository: MessageRepository,
    private val chatService: ChatService,
    private val webSocketTemplate: SimpMessagingTemplate
) {

    private val logger = KotlinLogging.logger { }

    fun createMessage(chatId: Long, createMessageRequest: CreateMessageRequest) {
        val message =
            messageRepository.save(
                Message(
                    Chat(id = chatId, spaceId = chatService.getChatById(chatId)),
                    createMessageRequest.content,
                    getUserIdFromSecurityContext()
                )
            )
        val destination = "/topic/chat/${message.chat.id}"
        logger.debug { "SENDING TO $destination" }
        webSocketTemplate.convertAndSend(destination, message.toMessageResponse())
    }

    fun getMessages(chatId: Long, pageable: Pageable) =
        messageRepository.findAllByChatIdOrderByCreatedAtDesc(chatId, pageable).toMessagePageResponse()

}