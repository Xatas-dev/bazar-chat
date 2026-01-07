package org.bazar.chat.service

import jakarta.transaction.Transactional
import org.bazar.chat.persistence.entity.Chat
import org.bazar.chat.persistence.repository.ChatRepository
import org.bazar.chat.utils.exceptions.ApiException
import org.bazar.chat.utils.exceptions.ApiExceptions
import org.bazar.chat.utils.extensions.toChatResponse
import org.springframework.stereotype.Service

@Service
@Transactional
class ChatService(
    private val chatRepository: ChatRepository
) {

    fun createChat(spaceId: Long) = chatRepository.save(Chat(spaceId)).toChatResponse()

    fun getChatById(chatId: Long) = chatRepository.findById(chatId).orElseThrow { ApiException(ApiExceptions.CHAT_NOT_FOUND) }.spaceId

    fun getChatBySpaceId(spaceId: Long) =
        chatRepository.findBySpaceId(spaceId).orElseThrow { ApiException(ApiExceptions.CHAT_NOT_FOUND) }
            .toChatResponse()

}