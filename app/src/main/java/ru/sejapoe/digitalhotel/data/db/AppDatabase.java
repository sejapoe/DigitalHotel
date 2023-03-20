package ru.sejapoe.digitalhotel.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ru.sejapoe.digitalhotel.data.model.Session;

@Database(entities = {Session.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE = null;

    public abstract SessionDao sessionDao();

    public static AppDatabase getInstance(Context context) {
        AppDatabase tempInstance = INSTANCE;
        if (tempInstance != null) return INSTANCE;
        tempInstance = Room.databaseBuilder(context.getApplicationContext(),  AppDatabase.class, "app_database").build();
        INSTANCE = tempInstance;
        return tempInstance;
    }
}
