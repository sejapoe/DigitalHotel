package ru.sejapoe.digitalhotel.ui.main.booking.bookableooms;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ru.sejapoe.digitalhotel.data.repository.HotelRepository;

@HiltViewModel
public class BookableRoomsViewModel extends ViewModel {
    private final HotelRepository hotelRepository;

    @Inject
    public BookableRoomsViewModel(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public LiveData<Void> book(int hotelId, String checkIn, String checkOut, int roomTypeId) {
        return hotelRepository.book(hotelId, checkIn, checkOut, roomTypeId);
    }
}
