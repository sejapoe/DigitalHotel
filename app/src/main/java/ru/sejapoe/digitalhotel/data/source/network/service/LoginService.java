package ru.sejapoe.digitalhotel.data.source.network.service;

import androidx.core.util.Pair;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.sejapoe.digitalhotel.data.repository.LoginRepository;
import ru.sejapoe.digitalhotel.data.source.network.AuthorizationRequired;

public interface LoginService {
    @POST("/register/start")
    Call<String> startRegistration(@Body Pair<String, String> body);

    @POST("/register/finish")
    Call<Void> finishRegistration(@Body Pair<String, String> body);

    @POST("/login/start")
    Call<LoginRepository.LoginServerResponse> startLogin(@Body Pair<String, String> body);

    @POST("/login/finish")
    Call<String> finishLogin(@Body String body);

    @AuthorizationRequired
    @POST("/logout")
    Call<Void> logOut();

    @AuthorizationRequired
    @POST("/subscribe")
    Call<Void> subscribe(@Body String token);
}
