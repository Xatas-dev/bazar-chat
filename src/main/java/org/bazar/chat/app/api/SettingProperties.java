package org.bazar.chat.app.api;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties("settings")
public class SettingProperties {
    private Scheduler scheduler;
    private Message message;
    private PushSubscription pushSubscription;
    private Encryption encryption;

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

    @Data
    public static class Encryption {
        private Chat chat;
        private Message message;

        @Data
        public static class Chat {
            private String currentVersion;
            private Map<String, String> keys;
        }

        @Data
        public static class Message {
            private Short currentVersion;
        }
    }
}
