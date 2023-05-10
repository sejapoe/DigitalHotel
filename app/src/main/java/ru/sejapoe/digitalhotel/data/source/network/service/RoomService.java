package ru.sejapoe.digitalhotel.data.source.network.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.sejapoe.digitalhotel.data.model.hotel.room.RoomAccess;
import ru.sejapoe.digitalhotel.data.source.network.Authenticated;

@Authenticated
public interface RoomService {
    @GET("/accesses")
    Call<List<RoomAccess>> getAccesses();

    @POST("/room/{id}/open")
    Call<Void> open(@Path("id") int id);
}
