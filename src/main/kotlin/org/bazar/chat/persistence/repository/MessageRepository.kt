package org.bazar.chat.persistence.repository

import org.bazar.chat.persistence.entity.Message
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository: JpaRepository<Message, Long> {

    fun findAllByChatIdOrderByCreatedAtDesc(
        chatId: Long,
        pageable: Pageable
    ): Page<Message>

    fun deleteAllByChatId(chatId: Long)
}