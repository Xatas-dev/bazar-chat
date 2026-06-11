package org.bazar.chat.app.impl.pushsubscription;

import lombok.RequiredArgsConstructor;
import org.bazar.chat.app.api.SettingProperties;
import org.bazar.chat.app.api.pushsubscription.GetPushPublicKeyInbound;
import org.springframework.stereotype.Component;

/**
 * Реализация интерфейса по получению публичного ключа Web Push
 */
@Component
@RequiredArgsConstructor
public class GetPushPublicKeyUseCase implements GetPushPublicKeyInbound {
    private final SettingProperties settingProperties;

    @Override
    public String execute() {
        return settingProperties.getPushSubscription().getPublicKey();
    }
}
