package ru.sejapoe.digitalhotel.data.source.network.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import ru.sejapoe.digitalhotel.data.model.user.FriendRequests;
import ru.sejapoe.digitalhotel.data.model.user.User;
import ru.sejapoe.digitalhotel.data.model.user.UserInfo;
import ru.sejapoe.digitalhotel.data.model.user.UserLess;
import ru.sejapoe.digitalhotel.data.source.network.Authenticated;

@Authenticated
public interface UserService {
    @POST("/subscribe")
    Call<Void> subscribe(@Body String token);

    @GET("/ping")
    Call<Void> ping();

    @POST("/user/info")
    Call<Void> setInfo(@Body UserInfo userInfo);

    @GET("/user")
    Call<User> getUser();

    @GET("/friends")
    Call<List<UserLess>> getFriends();

    @GET("/friend/requests")
    Call<FriendRequests> getFriendRequests();

    @POST("/friend/request")
    Call<Void> sendOrAcceptRequest(@Body String targetUsername);
}
