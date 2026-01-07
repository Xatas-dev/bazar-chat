package org.bazar.chat.utils.extensions

import org.bazar.chat.model.ChatResponse
import org.bazar.chat.model.MessagePageResponse
import org.bazar.chat.model.MessageResponse
import org.springframework.http.ResponseEntity

fun ChatResponse.toResponseEntity() = ResponseEntity.ok(this)

fun MessageResponse.toResponseEntity() = ResponseEntity.ok(this)

fun MessagePageResponse.toResponseEntity() = ResponseEntity.ok(this)