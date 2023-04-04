package ru.sejapoe.digitalhotel.ui.main.booking;


import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;

public class BookingViewModel extends ViewModel {
    private final MutableLiveData<BookingDates> bookingDates =
            new MutableLiveData<>(new BookingDates());

    private final MutableLiveData<GuestsCount> guestsCount = new MutableLiveData<>(new GuestsCount());

    public void setGuestsCount(int adultsCount, int childrenCount) {
        guestsCount.postValue(new GuestsCount(adultsCount, childrenCount));
    }

    public void setBookingDates(Pair<Long, Long> bookingDates) {
        this.bookingDates.postValue(new BookingDates(bookingDates));
    }

    public LiveData<BookingDates> getBookingDates() {
        return bookingDates;
    }

    public LiveData<GuestsCount> getGuestsCount() {
        return guestsCount;
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

    public static class GuestsCount {
        private final int adultsCount;
        private final int childrenCount;

        public GuestsCount() {
            this(1, 0);
        }

        public GuestsCount(int adultsCount, int childrenCount) {
            this.adultsCount = adultsCount;
            this.childrenCount = childrenCount;
        }

        public int getAdultsCount() {
            return adultsCount;
        }

        public int getChildrenCount() {
            return childrenCount;
        }
    }
}
