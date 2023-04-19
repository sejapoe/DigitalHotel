package ru.sejapoe.digitalhotel.ui.main.booking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ru.sejapoe.digitalhotel.data.model.hotel.Booking;
import ru.sejapoe.digitalhotel.data.repository.HotelRepository;

@HiltViewModel
public class BookingViewModel extends ViewModel {
    private final HotelRepository hotelRepository;

    @Inject
    public BookingViewModel(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public LiveData<List<Booking>> getBookings() {
        return hotelRepository.getBookings();
    }
}