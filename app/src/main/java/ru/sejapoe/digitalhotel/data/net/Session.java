package ru.sejapoe.digitalhotel.data.net;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.io.Serializable;

import javax.annotation.Nonnull;

import ru.sejapoe.digitalhotel.data.auth.BitArray256;

@Entity
public class Session {
    @PrimaryKey
    @ColumnInfo(name = "session_id")
    @NonNull
    private final String sessionId;
    @ColumnInfo(name = "session_key")
    @TypeConverters(BitArray256.class)
    private final BitArray256 sessionKey;

    public Session(@NonNull String sessionId, BitArray256 sessionKey) {
        this.sessionId = sessionId;
        this.sessionKey = sessionKey;
    }

    @NonNull
    public String getSessionId() {
        return sessionId;
    }

    public BitArray256 getSessionKey() {
        return sessionKey;
    }
}
