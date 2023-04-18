package ru.sejapoe.digitalhotel.data.source.network.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.sejapoe.digitalhotel.data.model.hotel.BookableRoom;
import ru.sejapoe.digitalhotel.data.model.hotel.HotelLess;
import ru.sejapoe.digitalhotel.data.model.hotel.Reservation;
import ru.sejapoe.digitalhotel.data.source.network.AuthorizationRequired;

public interface HotelService {
    @GET("/hotels")
    Call<List<HotelLess>> getHotels();

    @GET("/reservations/{hotelId}/{checkIn}/{checkOut}")
    Call<List<BookableRoom>> getReservations(@Path("hotelId") int hotelId, @Path("checkIn") String checkIn, @Path("checkOut") String checkOut);

    @AuthorizationRequired
    @POST("/book/{hotelId}/{checkIn}/{checkOut}/{roomTypeId}")
    Call<Void> book(@Path("hotelId") int hotelId, @Path("checkIn") String checkIn, @Path("checkOut") String checkOut, @Path("roomTypeId") int roomTypeId);

    @AuthorizationRequired
    @GET("/reservations")
    Call<List<Reservation>> getReservations();
}
