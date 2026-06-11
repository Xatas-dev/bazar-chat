package org.bazar.chat.app.impl.pushsubscription;

import org.bazar.chat.app.api.pushsubscription.PresenceService;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Реализация сервиса управления присутствием пользователя в чате
 */
@Component
public class PresenceServiceImpl implements PresenceService {
    private final Map<String, SessionInfo> sessions = new ConcurrentHashMap<>();

    @Override
    public void register(UUID userId, Long chatId, String sessionId) {
        sessions.put(sessionId, new SessionInfo(userId, chatId));
    }

    @Override
    public void unregister(String sessionId) {
        sessions.remove(sessionId);
    }

    @Override
    public boolean isViewingChat(UUID userId, Long chatId) {
        return sessions.values()
                .stream()
                .anyMatch(session -> session.userId().equals(userId) && session.chatId().equals(chatId));
    }

    private record SessionInfo(
            UUID userId,
            Long chatId
    ) {}
}
