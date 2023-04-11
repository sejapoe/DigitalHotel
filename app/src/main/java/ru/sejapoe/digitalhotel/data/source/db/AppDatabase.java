package ru.sejapoe.digitalhotel.data.source.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.sejapoe.digitalhotel.data.source.db.dao.SessionDao;
import ru.sejapoe.digitalhotel.data.source.db.entity.SessionEntity;

@Database(entities = {SessionEntity.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    //    private static volatile AppDatabase INSTANCE = null;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public abstract SessionDao sessionDao();

//    public static AppDatabase getInstance(Context context) {
//        if (INSTANCE == null) {
//            synchronized (AppDatabase.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                                    AppDatabase.class, "app_database")
//                            .build();
//                }
//            }
//        }
//        return INSTANCE;
//
//    }
}
