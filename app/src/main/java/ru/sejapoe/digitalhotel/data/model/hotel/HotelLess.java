package ru.sejapoe.digitalhotel.data.model.hotel;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class HotelLess implements Serializable {
    private final int id;
    private final String name;

    public HotelLess(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
