package org.bazar.chat.fw;

import lombok.RequiredArgsConstructor;
import nl.martijndwars.webpush.PushService;
import org.bazar.chat.app.api.SettingProperties;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Security;

@Configuration
@RequiredArgsConstructor
public class WebPushConfiguration {
    private final SettingProperties properties;

    @Bean
    public PushService pushService() throws Exception {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
        PushService service = new PushService();

        service.setSubject(properties.getPushSubscription().getSubject());
        service.setPublicKey(properties.getPushSubscription().getPublicKey());
        service.setPrivateKey(properties.getPushSubscription().getPrivateKey());

        return service;
    }
}
