package ru.sejapoe.digitalhotel.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sejapoe.digitalhotel.data.model.hotel.BookableRoom;
import ru.sejapoe.digitalhotel.data.model.hotel.HotelLess;
import ru.sejapoe.digitalhotel.data.source.network.service.HotelService;

@Singleton
public class HotelRepository {
    private final HotelService hotelService;

    @Inject
    public HotelRepository(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    public LiveData<List<HotelLess>> getHotels() {
        MutableLiveData<List<HotelLess>> hotelsLiveData = new MutableLiveData<>();
        hotelService.getHotels().enqueue(
                new Callback<List<HotelLess>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<HotelLess>> call, @NonNull Response<List<HotelLess>> response) {
                        if (response.isSuccessful()) {
                            List<HotelLess> hotels = response.body();
                            if (hotels != null) {
                                hotelsLiveData.postValue(hotels);
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<HotelLess>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        hotelsLiveData.postValue(Collections.emptyList());
                    }
                }
        );
        return hotelsLiveData;
    }

    public LiveData<List<BookableRoom>> getBookableRooms(int hotelId, String checkIn, String checkOut) {
        MutableLiveData<List<BookableRoom>> reservationsLiveData = new MutableLiveData<>();
        hotelService.getReservations(hotelId, checkIn, checkOut).enqueue(
                new Callback<List<BookableRoom>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<BookableRoom>> call, @NonNull Response<List<BookableRoom>> response) {
                        if (response.isSuccessful()) {
                            List<BookableRoom> reservations = response.body();
                            if (reservations != null) {
                                reservationsLiveData.postValue(reservations);
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<BookableRoom>> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        reservationsLiveData.postValue(Collections.emptyList());
                    }
                }
        );
        return reservationsLiveData;
    }

    public void book(int hotelId, String checkIn, String checkOut, int roomTypeId) {
        new Thread(() -> {
            try {
                hotelService.book(hotelId, checkIn, checkOut, roomTypeId).execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
