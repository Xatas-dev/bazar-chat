package org.bazar.chat.persistence.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.ColumnDefault
import java.time.Instant

@Entity
@Table(name = "chat")
class Chat(
    @Column(name = "space_id", nullable = false)
    val spaceId: Long,

    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now(),

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null
)