package org.bazar.chat.fw;

import org.bazar.authorization.sdk.BazarAuthorizationClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BazarAuthorizationConfiguration {
    @Value("${service.authorization.host}")
    public String host;
    @Value("${service.authorization.port}")
    public int port;

    @Bean
    public BazarAuthorizationClient bazarAuthorizationClient() {
        return BazarAuthorizationClient.builder()
                .host(host)
                .port(port)
                .build();
    }
}
