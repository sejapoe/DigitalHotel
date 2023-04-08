package ru.sejapoe.digitalhotel.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import ru.sejapoe.digitalhotel.utils.BitArray256;

@Entity
public class Session {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private final int id;

    @ColumnInfo(name = "session_id")
    private final int sessionId;
    @ColumnInfo(name = "session_key")
    @TypeConverters(BitArray256.class)
    private final BitArray256 sessionKey;

    @Ignore
    public Session(int sessionId, BitArray256 sessionKey) {
        this(sessionId, sessionKey, 0);
    }

    public Session(int sessionId, BitArray256 sessionKey, int id) {
        this.sessionId = sessionId;
        this.sessionKey = sessionKey;
        this.id = id;
    }

    public int getSessionId() {
        return sessionId;
    }

    public BitArray256 getSessionKey() {
        return sessionKey;
    }

    public int getId() {
        return id;
    }
}
