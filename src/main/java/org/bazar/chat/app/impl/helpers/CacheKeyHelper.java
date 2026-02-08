package org.bazar.chat.app.impl.helpers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CacheKeyHelper {
    public static String buildUserIdsKey(List<UUID> userIds) {
        return userIds.stream()
                .sorted()
                .map(UUID::toString)
                .collect(Collectors.joining(","));
    }
}
