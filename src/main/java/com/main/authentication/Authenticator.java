package com.main.authentication;

import com.main.dto.user.SessionInfo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Authenticator {

    private Map<UUID, SessionInfo> sessions = new ConcurrentHashMap<>();

    private final static int SESSION_TIME_OUT = 1000 * 60 * 30;

    public final static int SESSION_TIME_OUT_MINUTES = SESSION_TIME_OUT / (1000 * 60);

//    @Autowired
//    private Clock clock;

    public UUID createSession(SessionInfo session) {
        UUID uuid = UUID.randomUUID();
        sessions.put(uuid, session);

        return uuid;
    }

    public SessionInfo getSession(UUID authToken) {
        return sessions.get(authToken);
    }

    @Scheduled(fixedDelay = SESSION_TIME_OUT)
    private void cleanUpSessions() {
        // sessions.entrySet().removeIf(session -> session.getValue().getDate().isBefore(LocalDateTime.now(clock)));
    }

}