package ru.sejapoe.digitalhotel.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sejapoe.digitalhotel.data.model.hotel.HotelLess;
import ru.sejapoe.digitalhotel.data.model.hotel.booking.BookableRoom;
import ru.sejapoe.digitalhotel.data.model.hotel.booking.Booking;
import ru.sejapoe.digitalhotel.data.source.network.service.HotelService;
import ru.sejapoe.digitalhotel.utils.LiveDataUtils;

@Singleton
public class HotelRepository {
    private final HotelService hotelService;

    private final Map<Integer, MutableLiveData<Boolean>> waitingForCheckIn = new HashMap<>();

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

    public LiveData<List<Booking>> getBookings() {
        return LiveDataUtils.callToListLiveData(hotelService.getBookings());
    }

    public LiveData<List<BookableRoom>> getBookableRooms(int hotelId, String checkIn, String checkOut) {
        return LiveDataUtils.callToListLiveData(hotelService.getBookings(hotelId, checkIn, checkOut));
    }

    public LiveData<Void> book(int hotelId, String checkIn, String checkOut, int roomTypeId) {
        return LiveDataUtils.callToBodyLiveData(hotelService.book(hotelId, checkIn, checkOut, roomTypeId));
    }

    public LiveData<Booking> getBooking(int id) {
        return LiveDataUtils.callToBodyLiveData(hotelService.getBooking(id));
    }

    public LiveData<Boolean> deleteBooking(int id) {
        return LiveDataUtils.callToSuccessLiveData(hotelService.deleteBooking(id));
    }

    public LiveData<Boolean> payBooking(int id) {
        return LiveDataUtils.callToSuccessLiveData(hotelService.payBooking(id));
    }

    public LiveData<Boolean> checkIn(int id) {
        return LiveDataUtils.callToSuccessLiveData(hotelService.checkIn(id));
    }

    public LiveData<Boolean> isCheckedIn(int id) {
        MutableLiveData<Boolean> isCheckedIn = new MutableLiveData<>();
        waitingForCheckIn.put(id, isCheckedIn);
        return Transformations.map(isCheckedIn, aBoolean -> {
            if (aBoolean) waitingForCheckIn.remove(id);
            return aBoolean;
        });
    }

    public void setCheckedIn(int bookingId) {
        MutableLiveData<Boolean> isCheckedIn = waitingForCheckIn.get(bookingId);
        if (isCheckedIn != null) isCheckedIn.postValue(true);
    }
}
