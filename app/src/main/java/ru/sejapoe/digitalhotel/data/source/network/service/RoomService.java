package ru.sejapoe.digitalhotel.data.source.network.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.sejapoe.digitalhotel.data.model.hotel.room.Occupation;
import ru.sejapoe.digitalhotel.data.source.network.Authenticated;

@Authenticated
public interface RoomService {
    @GET("/occupations")
    Call<List<Occupation>> getOccupations();

    @POST("/room/{id}/open")
    Call<Void> open(@Path("id") int id);
}
