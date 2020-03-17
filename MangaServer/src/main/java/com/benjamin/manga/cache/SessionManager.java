package com.benjamin.manga.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@NoArgsConstructor
public class SessionManager {

    private static Map<String, Session> SESSIONS = new ConcurrentHashMap<>();

    public static String putSession(Session session){
        SESSIONS.put(session.getToken(), session);
        return session.getToken();
    }

    public static Session getSession(String token){
        return SESSIONS.get(token);
    }

    public static void removeSession(String token){
        SESSIONS.remove(token);
    }
}
