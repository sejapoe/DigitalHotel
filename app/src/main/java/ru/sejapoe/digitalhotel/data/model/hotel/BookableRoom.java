package ru.sejapoe.digitalhotel.data.model.hotel;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class BookableRoom implements Parcelable {
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
}
