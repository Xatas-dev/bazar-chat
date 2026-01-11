package org.bazar.chat.persistence.repository

import org.bazar.chat.persistence.entity.Chat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ChatRepository: JpaRepository<Chat, Long> {

    fun findBySpaceId(spaceId: Long) : Optional<Chat>
}