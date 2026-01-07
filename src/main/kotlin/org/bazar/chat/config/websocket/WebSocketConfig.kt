package org.bazar.chat.config.websocket

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {

    override fun configureMessageBroker(config: MessageBrokerRegistry) {
        config.enableSimpleBroker("/topic")
        config.setApplicationDestinationPrefixes("/in")
    }

    // 2. Регистрация HTTP-эндпоинта для подключения
    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        // Клиенты будут подключаться по адресу: ws://localhost:8082/ws
        registry.addEndpoint("/ws")
            .setAllowedOriginPatterns("*")

    }
}