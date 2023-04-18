package ru.sejapoe.digitalhotel.data.source.db.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import ru.sejapoe.digitalhotel.utils.BitArray256;

@Entity(tableName = "session")
public class SessionEntity {
    @PrimaryKey
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "session_id")
    public int sessionId;
    @ColumnInfo(name = "session_key")
    @TypeConverters(BitArray256.class)
    public BitArray256 sessionKey;
}
