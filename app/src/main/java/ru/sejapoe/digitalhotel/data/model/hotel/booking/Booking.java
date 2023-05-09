package ru.sejapoe.digitalhotel.data.model.hotel.booking;

import java.time.LocalDate;

import ru.sejapoe.digitalhotel.data.model.hotel.HotelLess;
import ru.sejapoe.digitalhotel.data.model.hotel.Payment;
import ru.sejapoe.digitalhotel.data.model.hotel.room.RoomType;

public class Booking {
    private final int id;
    private final HotelLess hotel;
    private final RoomType roomType;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;
    private final boolean isCanceled;
    private final Payment payment;

    public Booking(int id, HotelLess hotel, RoomType roomType, LocalDate checkInDate, LocalDate checkOutDate, boolean isCanceled, Payment payment) {
        this.id = id;
        this.hotel = hotel;
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.isCanceled = isCanceled;
        this.payment = payment;
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

    public boolean isCanceled() {
        return isCanceled;
    }

    public Payment getPayment() {
        return payment;
    }
}
