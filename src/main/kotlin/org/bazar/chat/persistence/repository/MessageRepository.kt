package org.bazar.chat.persistence.repository

import org.bazar.chat.persistence.entity.Message
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface MessageRepository: JpaRepository<Message, Long> {

    fun findAllByChatIdOrderByCreatedAtDesc(
        chatId: Long,
        pageable: Pageable
    ): Page<Message>
}