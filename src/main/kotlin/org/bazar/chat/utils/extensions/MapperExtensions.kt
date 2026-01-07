package org.bazar.chat.utils.extensions

import org.bazar.chat.model.ChatResponse
import org.bazar.chat.model.MessagePageResponse
import org.bazar.chat.model.MessageResponse
import org.bazar.chat.persistence.entity.Chat
import org.bazar.chat.persistence.entity.Message
import org.springframework.data.domain.Page


fun Chat.toChatResponse() = ChatResponse(id!!, spaceId, createdAt)

fun Message.toMessageResponse() = MessageResponse(id!!, chat.id!!, userId, content, createdAt)

fun Page<Message>.toMessagePageResponse() =
    MessagePageResponse(
        content = content.map { it.toMessageResponse() },
        page = number,
        pageSize = size,
        totalElements = totalElements,
        totalPages = totalPages
    )