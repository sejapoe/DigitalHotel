package ru.sejapoe.digitalhotel.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import ru.sejapoe.digitalhotel.data.model.Session;

@Dao
public interface SessionDao {
    @Query("SELECT * FROM session LIMIT 1")
    Session get();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void set(Session session);

    @Query("DELETE FROM session")
    void drop();
}
