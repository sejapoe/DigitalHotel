package ru.sejapoe.digitalhotel.data.model.hotel;

import java.time.LocalDate;

public class Reservation {
    private final int id;
    private final HotelLess hotel;
    private final RoomType roomType;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;

    public Reservation(int id, HotelLess hotel, RoomType roomType, LocalDate checkInDate, LocalDate checkOutDate) {
        this.id = id;
        this.hotel = hotel;
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public int getId() {
        return id;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public HotelLess getHotel() {
        return hotel;
    }
}
