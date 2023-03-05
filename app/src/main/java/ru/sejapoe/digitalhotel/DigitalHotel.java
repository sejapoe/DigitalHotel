package ru.sejapoe.digitalhotel;

import android.app.Application;
import android.util.Log;

import java.util.List;

import ru.sejapoe.digitalhotel.data.AppDatabase;
import ru.sejapoe.digitalhotel.data.net.HttpProvider;
import ru.sejapoe.digitalhotel.data.net.Session;
import ru.sejapoe.digitalhotel.data.net.SessionDao;

public class DigitalHotel extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SessionDao sessionDao = AppDatabase.getInstance(this).sessionDao();
        new Thread(() -> {
            List<Session> sessions = sessionDao.getAll();
            Log.d("DigitalHotelApplication", String.valueOf(sessions));
            HttpProvider.createInstance(sessions.isEmpty() ? null : sessions.get(0));
        }).start();
    }
}
