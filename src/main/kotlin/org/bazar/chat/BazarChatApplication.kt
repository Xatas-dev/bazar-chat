package org.bazar.chat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BazarChatApplication

fun main(args: Array<String>) {
    runApplication<BazarChatApplication>(*args)
}
