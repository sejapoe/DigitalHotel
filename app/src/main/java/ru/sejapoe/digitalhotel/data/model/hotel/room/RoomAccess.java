package ru.sejapoe.digitalhotel.data.model.hotel.room;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.time.LocalDate;

import ru.sejapoe.digitalhotel.data.model.hotel.access.RightsComposition;

public class RoomAccess implements Serializable {
    private final Room room;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;
    private final int rights;

    public RoomAccess(Room room, LocalDate checkInDate, LocalDate checkOutDate, int rights) {
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.rights = rights;
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

    @NonNull
    public RightsComposition getRights() {
        return new RightsComposition(rights);
    }

    @NonNull
    @Override
    public String toString() {
        return "RoomAccess{" +
                "room=" + room +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", rights=" + rights +
                '}';
    }
}
