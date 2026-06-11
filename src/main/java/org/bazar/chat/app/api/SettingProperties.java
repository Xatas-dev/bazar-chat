package org.bazar.chat.app.api;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("settings")
public class SettingProperties {
    private Scheduler scheduler;
    private Message message;
    private PushSubscription pushSubscription;

    @Data
    public static class Scheduler {
        private String deleteMessages;
    }

    @Data
    public static class Message {
        private Integer replyPreviewLength;
    }

    @Data
    public static class PushSubscription {
        private String publicKey;
        private String privateKey;
        private String subject;
    }
}
