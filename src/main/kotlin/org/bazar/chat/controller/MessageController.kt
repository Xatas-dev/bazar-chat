package org.bazar.chat.controller

import org.bazar.chat.api.MessagesApi
import org.bazar.chat.model.CreateMessageRequest
import org.bazar.chat.model.MessagePageResponse
import org.bazar.chat.model.MessageResponse
import org.bazar.chat.service.MessageService
import org.bazar.chat.utils.extensions.toResponseEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageController(
    private val messageService: MessageService
) : MessagesApi {

    override fun createMessage(
        chatId: Long,
        createMessageRequest: CreateMessageRequest
    ): ResponseEntity<Unit> {
        messageService.createMessage(chatId, createMessageRequest)
        return ResponseEntity.ok().build()
    }

    override fun getChatMessages(
        chatId: Long,
        @PageableDefault(size = 20, page = 0) pageable: Pageable?
    ): ResponseEntity<MessagePageResponse> =
        messageService.getMessages(chatId, pageable!!).toResponseEntity()
}