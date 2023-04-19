package ru.sejapoe.digitalhotel.data.source.network.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.sejapoe.digitalhotel.data.model.hotel.BookableRoom;
import ru.sejapoe.digitalhotel.data.model.hotel.Booking;
import ru.sejapoe.digitalhotel.data.model.hotel.HotelLess;
import ru.sejapoe.digitalhotel.data.source.network.Authenticated;

public interface HotelService {
    @GET("/hotels")
    Call<List<HotelLess>> getHotels();

    @GET("/book/{hotelId}/{checkIn}/{checkOut}")
    Call<List<BookableRoom>> getBookings(@Path("hotelId") int hotelId, @Path("checkIn") String checkIn, @Path("checkOut") String checkOut);

    @Authenticated
    @POST("/book/{hotelId}/{checkIn}/{checkOut}/{roomTypeId}")
    Call<Void> book(@Path("hotelId") int hotelId, @Path("checkIn") String checkIn, @Path("checkOut") String checkOut, @Path("roomTypeId") int roomTypeId);

    @Authenticated
    @GET("/bookings")
    Call<List<Booking>> getBookings();

    @Authenticated
    @GET("/booking/{id}")
    Call<Booking> getBooking(@Path("id") int id);

    @Authenticated
    @DELETE("/booking/{id}")
    Call<Void> deleteBooking(@Path("id") int id);

    @Authenticated
    @POST("/booking/{id}/pay")
    Call<Void> payBooking(@Path("id") int id);
}
