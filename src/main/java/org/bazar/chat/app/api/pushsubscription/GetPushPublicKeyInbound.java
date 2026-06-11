package org.bazar.chat.app.api.pushsubscription;

/**
 * Входной интерфейс по получению публичного ключа Web Push
 */
public interface GetPushPublicKeyInbound {
    /**
     * Получить публичный ключ Web Push
     *
     * @return Публичный ключ Web Push
     */
    String execute();
}
