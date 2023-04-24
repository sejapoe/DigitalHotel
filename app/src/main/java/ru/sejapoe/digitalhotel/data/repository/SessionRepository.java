package ru.sejapoe.digitalhotel.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.sejapoe.digitalhotel.data.model.login.Session;
import ru.sejapoe.digitalhotel.data.source.db.AppDatabase;
import ru.sejapoe.digitalhotel.data.source.db.dao.SessionDao;
import ru.sejapoe.digitalhotel.data.source.db.mapper.SessionMapper;

@Singleton
public class SessionRepository {
    private final SessionDao sessionDao;
    private Session session;

    @Inject
    public SessionRepository(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    public Session getSession() {
        if (session == null) {
            session = SessionMapper.fromEntity(sessionDao.get());
        }
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
        AppDatabase.databaseWriteExecutor.execute(() -> sessionDao.set(SessionMapper.toEntity(session)));
    }

    public void dropSession() {
        AppDatabase.databaseWriteExecutor.execute(sessionDao::drop);
    }
}
