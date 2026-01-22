package org.bazar.chat.fw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.bazar.chat")
public class BazarChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(BazarChatApplication.class, args);
    }
}
