package ru.sejapoe.digitalhotel.ui.main.booking.info;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ru.sejapoe.digitalhotel.data.model.hotel.booking.Booking;
import ru.sejapoe.digitalhotel.data.repository.HotelRepository;

@HiltViewModel
public class BookingInfoViewModel extends ViewModel {
    private final HotelRepository hotelRepository;

    @Inject
    public BookingInfoViewModel(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public LiveData<Booking> getBooking(int bookingId) {
        return hotelRepository.getBooking(bookingId);
    }

    public LiveData<Boolean> deleteBooking(int bookingId) {
        return hotelRepository.deleteBooking(bookingId);
    }

    public LiveData<Boolean> payBooking(int bookingId) {
        return hotelRepository.payBooking(bookingId);
    }

    public LiveData<Boolean> checkIn(int bookingId) {
        return hotelRepository.checkIn(bookingId);
    }

    public LiveData<Boolean> isCheckedIn(int bookingId) {
        return hotelRepository.isCheckedIn(bookingId);
    }
}
