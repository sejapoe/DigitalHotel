package ru.sejapoe.digitalhotel.data.source.network;

import com.github.leonardoxh.livedatacalladapter.LiveDataCallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import ru.sejapoe.digitalhotel.data.repository.SessionRepository;
import ru.sejapoe.digitalhotel.data.source.network.service.HotelService;
import ru.sejapoe.digitalhotel.data.source.network.service.LoginService;
import ru.sejapoe.digitalhotel.data.source.network.service.RoomService;

@Module
@InstallIn(SingletonComponent.class)
public class RetrofitModule {
    private static final String HOST = "http://192.168.0.15:8080";
//    private static final String HOST = "https://sejapoe.live";

    @Provides
    @Singleton
    public static Retrofit provideRetrofit(SessionRepository sessionRepository) {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new TokenInterceptor(sessionRepository))
                .build();

        return new Retrofit.Builder()
                .baseUrl(HOST)
                .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    public static LoginService provideLoginService(Retrofit retrofit) {
        return retrofit.create(LoginService.class);
    }

    @Provides
    @Singleton
    public static HotelService provideHotelService(Retrofit retrofit) {
        return retrofit.create(HotelService.class);
    }

    @Provides
    @Singleton
    public static RoomService provideRoomService(Retrofit retrofit) {
        return retrofit.create(RoomService.class);
    }
}
