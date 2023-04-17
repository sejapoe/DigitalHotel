package ru.sejapoe.digitalhotel.data.source.network;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import ru.sejapoe.digitalhotel.data.source.network.service.HotelService;
import ru.sejapoe.digitalhotel.data.source.network.service.LoginService;

@Module
@InstallIn(SingletonComponent.class)
public class RetrofitModule {
    @Provides
    @Singleton
    public static RetrofitProvider provideRetrofitProvider() {
        return RetrofitProvider.getInstance();
    }

    @Provides
    @Singleton
    public static LoginService provideLoginService(RetrofitProvider retrofitProvider) {
        return retrofitProvider.createLoginService();
    }

    @Provides
    @Singleton
    public static HotelService provideHotelService(RetrofitProvider retrofitProvider) {
        return retrofitProvider.createHotelService();
    }
}
