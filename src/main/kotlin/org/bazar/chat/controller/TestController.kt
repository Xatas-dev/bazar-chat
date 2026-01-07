package org.bazar.chat.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.bazar.chat.model.MessageResponse
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import org.springframework.web.util.HtmlUtils


@Controller
class TestController {

    private val logger = KotlinLogging.logger { }

    @MessageMapping("/topic/out/chat/3")
    @Throws(Exception::class)
    fun greeting(message: MessageResponse) {
        logger.info { "MESSAGE YES $message" }
    }

}