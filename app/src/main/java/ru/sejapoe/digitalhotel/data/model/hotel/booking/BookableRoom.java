package ru.sejapoe.digitalhotel.data.model.hotel.booking;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;

import ru.sejapoe.digitalhotel.data.model.hotel.room.RoomType;

public class BookableRoom implements Parcelable, Comparable<BookableRoom> {
    private final int count;
    private final RoomType roomType;

    public BookableRoom(int count, RoomType roomType) {
        this.count = count;
        this.roomType = roomType;
    }

    protected BookableRoom(Parcel in) {
        count = in.readInt();
        roomType = in.readParcelable(RoomType.class.getClassLoader());
    }

    public static final Creator<BookableRoom> CREATOR = new Creator<BookableRoom>() {
        @Override
        public BookableRoom createFromParcel(Parcel in) {
            return new BookableRoom(in);
        }

        @Override
        public BookableRoom[] newArray(int size) {
            return new BookableRoom[size];
        }
    };

    public int getCount() {
        return count;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(this.count);
        dest.writeParcelable(this.roomType, flags);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookableRoom that = (BookableRoom) o;
        return count == that.count && Objects.equals(roomType, that.roomType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, roomType);
    }

    @Override
    public int compareTo(@NonNull BookableRoom o) {
        return Integer.compare(roomType.getId(), o.roomType.getId());
    }
}
