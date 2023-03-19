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
        new Thread(() -> {
            Log.d("DigitalHotelApplication", "Waiting got database");
            SessionDao sessionDao = AppDatabase.getInstance(this).sessionDao();
            Session session = sessionDao.get();
            Log.d("DigitalHotelApplication", String.valueOf(session));
            HttpProvider.createInstance(session);
        }).start();
    }
}
