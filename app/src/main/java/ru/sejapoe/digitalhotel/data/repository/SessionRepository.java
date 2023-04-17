package ru.sejapoe.digitalhotel.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.sejapoe.digitalhotel.data.model.login.Session;
import ru.sejapoe.digitalhotel.data.source.db.AppDatabase;
import ru.sejapoe.digitalhotel.data.source.db.dao.SessionDao;
import ru.sejapoe.digitalhotel.data.source.db.mapper.SessionMapper;
import ru.sejapoe.digitalhotel.data.source.network.RetrofitProvider;

@Singleton
public class SessionRepository {
    private final SessionDao sessionDao;

    @Inject
    public SessionRepository(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    public LiveData<Session> getSession() {
        return Transformations.map(sessionDao.get(), SessionMapper::fromEntity);
    }

    public void setSession(Session session) {
        AppDatabase.databaseWriteExecutor.execute(() -> sessionDao.set(SessionMapper.toEntity(session)));
        RetrofitProvider.getInstance().setSession(session);
    }

    public void dropSession() {
        AppDatabase.databaseWriteExecutor.execute(sessionDao::drop);
        RetrofitProvider.getInstance().setSession(null);
    }
}
