package ru.sejapoe.digitalhotel.data.model.hotel.access;

public enum Rights {
    ACCESS_ROOM(1), // open and close doors
    MANAGE_ROOM(2), // set dnd
    MANAGE_RESERVATIONS(4),
    PAY_FOR_SERVICES(8),
    MANAGE_ACCESS(128);
    private final int value;

    Rights(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
