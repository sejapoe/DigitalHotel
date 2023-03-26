package ru.sejapoe.digitalhotel.data.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider {
    private static final String HOST = "https://sejapoe.live";
    private static final OkHttpClient okHttpClient = new OkHttpClient();

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();

    public static LoginService createLoginService() {
        return retrofit.create(LoginService.class);
    }
}
