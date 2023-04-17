package ru.sejapoe.digitalhotel.data.source.network.service;

import retrofit2.Call;
import retrofit2.http.POST;
import ru.sejapoe.digitalhotel.data.source.network.AuthorizationRequired;

public interface RoomService {
    @AuthorizationRequired
    @POST("/test")
    Call<Void> test();
}
