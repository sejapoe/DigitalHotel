package ru.sejapoe.digitalhotel.data.source.network.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import ru.sejapoe.digitalhotel.data.model.hotel.AccessShareEdit;
import ru.sejapoe.digitalhotel.data.source.network.Authenticated;

@Authenticated
public interface AccessService {
    @POST("/room/{id}/share")
    Call<Void> shareAccess(@Path("id") int roomId, @Body String username);

    @PUT("/room/{id}/share/{shareId}")
    Call<Void> editShare(@Path("id") int roomId, @Path("shareId") int shareId, @Body AccessShareEdit accessShareEdit);

    @DELETE("/room/{id}/share/{shareId}")
    Call<Void> deleteShare(@Path("id") int roomId, @Path("shareId") int shareId);
}
