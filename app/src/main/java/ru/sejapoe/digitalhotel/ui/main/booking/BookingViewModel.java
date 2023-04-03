package ru.sejapoe.digitalhotel.ui.main.booking;


import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;

public class BookingViewModel extends ViewModel {
    private final MutableLiveData<BookingDates> bookingDates =
            new MutableLiveData<>(new BookingDates());

    public void setBookingDates(Pair<Long, Long> bookingDates) {
        this.bookingDates.postValue(new BookingDates(bookingDates));
    }

    public LiveData<BookingDates> getBookingDates() {
        return bookingDates;
    }

    public static class BookingDates {
        public static final long MILLIS_IN_DAY = 24 * 60 * 60 * 1000;
        private final LocalDate checkIn;
        private final LocalDate checkOut;

        public BookingDates() {
            this(
                    LocalDate.now(),
                    LocalDate.now().plusDays(1)
            );
        }

        public BookingDates(LocalDate checkIn, LocalDate checkOut) {
            this.checkIn = checkIn;
            this.checkOut = checkOut;
        }

        public BookingDates(long checkIn, long checkOut) {
            this(
                    LocalDate.ofEpochDay(checkIn / MILLIS_IN_DAY),
                    LocalDate.ofEpochDay(checkOut / MILLIS_IN_DAY)
            );
        }

        public BookingDates(Pair<Long, Long> bookingDates) {
            this(bookingDates.first, bookingDates.second);
        }

        public Pair<Long, Long> asMillisPair() {
            return new Pair<>(checkIn.toEpochDay() * MILLIS_IN_DAY, checkOut.toEpochDay() * MILLIS_IN_DAY);
        }

        public LocalDate getCheckIn() {
            return checkIn;
        }

        public LocalDate getCheckOut() {
            return checkOut;
        }
    }
}
