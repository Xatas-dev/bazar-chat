package org.bazar.chat.controller

import org.bazar.chat.api.ChatsApi
import org.bazar.chat.model.ChatResponse
import org.bazar.chat.model.CreateChatRequest
import org.bazar.chat.service.ChatService
import org.bazar.chat.utils.extensions.toResponseEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatController(
    private val chatService: ChatService
) : ChatsApi {

    override fun createChat(createChatRequest: CreateChatRequest): ResponseEntity<ChatResponse> =
        chatService.createChat(createChatRequest.spaceId).toResponseEntity()


    override fun getChatBySpace(spaceId: Long): ResponseEntity<ChatResponse> =
        chatService.getChatBySpaceId(spaceId).toResponseEntity()
}