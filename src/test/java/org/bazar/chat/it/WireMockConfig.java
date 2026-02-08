package org.bazar.chat.it;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class WireMockConfig {
    @Value("${service.bazar-persona.port}")
    private int bazarPersonaPort;

    @Bean(name = "bazarPersonaServer")
    public WireMockServer bazarPersonaServer() {
        return new WireMockServer(bazarPersonaPort);
    }
}
