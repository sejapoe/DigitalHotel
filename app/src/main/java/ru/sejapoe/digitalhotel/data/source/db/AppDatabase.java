package ru.sejapoe.digitalhotel.data.source.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import ru.sejapoe.digitalhotel.data.model.Session;

@Database(entities = {Session.class}, version = 3)
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
