package ru.sejapoe.digitalhotel.data.network;

import androidx.core.util.Pair;

import java.math.BigInteger;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import ru.sejapoe.digitalhotel.data.repository.LoginRepository;

public interface LoginService {
    @POST("/register/start")
    Call<String> startRegistration(@Body Pair<String, String> body);

    @POST("/register/finish")
    Call<Void> finishRegistration(@Body Pair<String, BigInteger> body);

    @POST("/login/start")
    Call<LoginRepository.LoginServerResponse> startLogin(@Body Pair<String, BigInteger> body);

    @POST("/login/finish")
    Call<String> finishLogin(@Body String body);

    @AuthorizationRequired
    @GET("/hello")
    Call<String> hello();
}
