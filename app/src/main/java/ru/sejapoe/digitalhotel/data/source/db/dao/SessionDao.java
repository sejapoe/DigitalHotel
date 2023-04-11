package ru.sejapoe.digitalhotel.data.source.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import ru.sejapoe.digitalhotel.data.source.db.entity.SessionEntity;

@Dao
public interface SessionDao {
    @Query("SELECT * FROM session LIMIT 1")
    LiveData<SessionEntity> get();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void set(SessionEntity session);

    @Query("DELETE FROM session")
    void drop();
}
