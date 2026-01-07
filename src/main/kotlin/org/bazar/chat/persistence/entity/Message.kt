package org.bazar.chat.persistence.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.annotations.ColumnDefault
import java.time.Instant
import java.util.*

@Entity
@Table(name = "message")
class Message(
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chat_id", nullable = false)
    val chat: Chat,

    @Size(max = 1028)
    @NotNull
    @Column(name = "content", nullable = false, length = 1028)
    var content: String,

    @Column(name = "user_id", nullable = false)
    val userId: UUID,

    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),

    @ColumnDefault("now()")
    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null
)