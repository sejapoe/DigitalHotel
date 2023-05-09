package ru.sejapoe.digitalhotel.data.model.hotel.booking;

import java.time.LocalDate;
import java.util.Objects;

import ru.sejapoe.digitalhotel.data.model.hotel.HotelLess;
import ru.sejapoe.digitalhotel.data.model.hotel.Payment;
import ru.sejapoe.digitalhotel.data.model.hotel.room.RoomType;

public class Booking implements Comparable<Booking> {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return id == booking.id && isCanceled == booking.isCanceled && Objects.equals(hotel, booking.hotel) && Objects.equals(roomType, booking.roomType) && Objects.equals(checkInDate, booking.checkInDate) && Objects.equals(checkOutDate, booking.checkOutDate) && Objects.equals(payment, booking.payment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hotel, roomType, checkInDate, checkOutDate, isCanceled, payment);
    }


    @Override
    public int compareTo(Booking o) {
        return Integer.compare(id, o.id);
    }
}
