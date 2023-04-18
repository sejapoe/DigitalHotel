package ru.sejapoe.digitalhotel.data.source.db.mapper;

import ru.sejapoe.digitalhotel.data.model.login.Session;
import ru.sejapoe.digitalhotel.data.source.db.entity.SessionEntity;

public class SessionMapper {
    public static SessionEntity toEntity(Session session) {
        SessionEntity entity = new SessionEntity();
        entity.id = 0; // Use a constant ID for the singleton session
        entity.sessionId = session.getSessionId();
        entity.sessionKey = session.getSessionKey();
        return entity;
    }

    public static Session fromEntity(SessionEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Session(entity.sessionId, entity.sessionKey);
    }
}
