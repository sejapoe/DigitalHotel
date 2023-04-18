package ru.sejapoe.digitalhotel.data.model.hotel;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class RoomType implements Parcelable {
    private final int id;
    private final String name;
    private final int price;
    private final int adultsCount;
    private final int childrenCount;

    public RoomType(int id, String name, int price, int adultsCount, int childrenCount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.adultsCount = adultsCount;
        this.childrenCount = childrenCount;
    }

    protected RoomType(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readInt();
        adultsCount = in.readInt();
        childrenCount = in.readInt();
    }

    public static final Creator<RoomType> CREATOR = new Creator<RoomType>() {
        @Override
        public RoomType createFromParcel(Parcel in) {
            return new RoomType(in);
        }

        @Override
        public RoomType[] newArray(int size) {
            return new RoomType[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getAdultsCount() {
        return adultsCount;
    }

    public int getChildrenCount() {
        return childrenCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.price);
        dest.writeInt(this.adultsCount);
        dest.writeInt(this.childrenCount);
    }
}
