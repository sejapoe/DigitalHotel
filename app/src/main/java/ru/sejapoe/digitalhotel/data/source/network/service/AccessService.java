package ru.sejapoe.digitalhotel.data.source.network.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import ru.sejapoe.digitalhotel.data.model.hotel.access.AccessShare;
import ru.sejapoe.digitalhotel.data.model.hotel.access.AccessShareEdit;
import ru.sejapoe.digitalhotel.data.source.network.Authenticated;

@Authenticated
public interface AccessService {
    @GET("/room/{id}/shares")
    Call<List<AccessShare>> getSharedAccesses(@Path("id") int roomId);

    @POST("/room/{id}/shares")
    Call<Void> shareAccess(@Path("id") int roomId, @Body List<String> usernames);

    @GET("/share/{shareId}")
    Call<AccessShare> getShare(@Path("shareId") int shareId);

    @PUT("/share/{shareId}")
    Call<Void> editShare(@Path("shareId") int shareId, @Body AccessShareEdit accessShareEdit);

    @DELETE("/share/{shareId}")
    Call<Void> deleteShare(@Path("shareId") int shareId);
}
