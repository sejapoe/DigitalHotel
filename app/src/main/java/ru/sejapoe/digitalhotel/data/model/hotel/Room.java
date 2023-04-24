package ru.sejapoe.digitalhotel.data.model.hotel;

public class Room {
    private final int id;
    private final int number;
    private final RoomType roomType;
    private final HotelLess hotel;

    private boolean isOpen;

    public Room(int id, int number, RoomType roomType, HotelLess hotel, boolean isOpen) {
        this.id = id;
        this.number = number;
        this.roomType = roomType;
        this.hotel = hotel;
        this.isOpen = isOpen;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public int getNumber() {
        return number;
    }

    public HotelLess getHotel() {
        return hotel;
    }

    public int getId() {
        return id;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
