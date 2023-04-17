package ru.sejapoe.digitalhotel.data.source.network;

import com.github.leonardoxh.livedatacalladapter.LiveDataCallAdapterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import ru.sejapoe.digitalhotel.data.model.login.Session;
import ru.sejapoe.digitalhotel.data.source.network.service.HotelService;
import ru.sejapoe.digitalhotel.data.source.network.service.LoginService;

public class RetrofitProvider {
    private static final String HOST = "http://192.168.0.15:8080";
    //    private static final String HOST = "https://sejapoe.live";
    private static RetrofitProvider INSTANCE;
    private final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new TokenInterceptor())
            .build();

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(HOST)
            .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();

    private Session session;

    private RetrofitProvider(Session session) {
        this.session = session;
    }

    public LoginService createLoginService() {
        return retrofit.create(LoginService.class);
    }

    public HotelService createHotelService() {
        return retrofit.create(HotelService.class);
    }

    public static void createInstance(Session session) {
        INSTANCE = new RetrofitProvider(session);
    }

    public static RetrofitProvider getInstance() {
        if (INSTANCE == null) {
            createInstance(null);
        }
        return INSTANCE;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
