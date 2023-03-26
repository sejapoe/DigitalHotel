package ru.sejapoe.digitalhotel.data.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import ru.sejapoe.digitalhotel.data.model.Session;

public class RetrofitProvider {
    private static final String HOST = "https://sejapoe.live"; //
    private static RetrofitProvider INSTANCE;
    private final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new TokenInterceptor())
            .build();

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(HOST)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();

    private Session session;

    public RetrofitProvider(Session session) {
        this.session = session;
    }

    public LoginService createLoginService() {
        return retrofit.create(LoginService.class);
    }

    public static void createInstance(Session session) {
        INSTANCE = new RetrofitProvider(session);
    }

    public static RetrofitProvider getInstance() {
        return INSTANCE;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
