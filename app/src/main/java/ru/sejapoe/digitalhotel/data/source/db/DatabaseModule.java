package ru.sejapoe.digitalhotel.data.source.db;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import ru.sejapoe.digitalhotel.data.source.db.dao.SessionDao;

@InstallIn(SingletonComponent.class)
@Module
public class DatabaseModule {
    @Provides
    public static SessionDao provideSessionDao(AppDatabase database) {
        return database.sessionDao();
    }

    @Provides
    @Singleton
    public static AppDatabase providesAppDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "app_database")
                .build();
    }
}
