package ru.sejapoe.digitalhotel.data.net;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SessionDao {
    @Query("SELECT * FROM session LIMIT 1")
    Session get();

    @Insert
    void set(Session session);
}
