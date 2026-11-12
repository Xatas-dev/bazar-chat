package org.bazar.chat.app.impl.service.encryption;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.SettingProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class KekProvider {
    private final SettingProperties properties;

    private Map<String, SecretKey> keyring;

    @PostConstruct
    void initialize() {
        Map<String, SecretKey> loadedKeys = new HashMap<>();

        properties.getEncryption().getChat().getKeys().forEach((version, encodedKey) -> {
            byte[] keyBytes = Base64.getDecoder().decode(encodedKey);

            if (keyBytes.length != 32) {
                throw new IllegalStateException("KEK must contain exactly 32 bytes: " + version);
            }

            loadedKeys.put(version, new SecretKeySpec(keyBytes, "AES"));
        });

        if (!loadedKeys.containsKey(properties.getEncryption().getChat().getCurrentVersion())) {
            throw new IllegalStateException("Current KEK version is not configured");
        }

        this.keyring = Map.copyOf(loadedKeys);
    }

    public SecretKey getCurrentKey() {
        return getKey(properties.getEncryption().getChat().getCurrentVersion());
    }

    public SecretKey getKey(String version) {
        SecretKey key = keyring.get(version);

        if (key == null) {
            throw new IllegalStateException("KEK version is not available: " + version);
        }

        return key;
    }

    public String getCurrentVersion() {
        return properties.getEncryption().getChat().getCurrentVersion();
    }
}
