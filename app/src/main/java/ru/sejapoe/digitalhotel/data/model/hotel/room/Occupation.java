package ru.sejapoe.digitalhotel.data.model.hotel.room;

import java.io.Serializable;
import java.time.LocalDate;

public class Occupation implements Serializable {
    private final int id;
    private final Room room;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;

    public Occupation(Room room, LocalDate checkInDate, LocalDate checkOutDate, int id) {
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public int getId() {
        return id;
    }
}
