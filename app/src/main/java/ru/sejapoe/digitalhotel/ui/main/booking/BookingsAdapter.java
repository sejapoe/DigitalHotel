package ru.sejapoe.digitalhotel.ui.main.booking;

import android.view.View;

import androidx.annotation.NonNull;

import java.time.LocalDate;

import ru.sejapoe.digitalhotel.R;
import ru.sejapoe.digitalhotel.data.model.hotel.booking.Booking;
import ru.sejapoe.digitalhotel.databinding.BookingItemBinding;
import ru.sejapoe.digitalhotel.ui.core.ClickableItemListAdapter;

public class BookingsAdapter extends ClickableItemListAdapter<BookingItemBinding, Booking> {
    public BookingsAdapter(OnItemClickListener<Booking> listener) {
        super(BookingItemBinding.class, Booking.class);
        setOnClickListener(listener);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingItemBinding binding, @NonNull Booking booking) {
        binding.hotelName.setText(booking.getHotel().getName());
        binding.roomType.setText(booking.getRoomType().getName());
        binding.price.setText(binding.getRoot().getResources().getString(R.string.price, booking.getRoomType().getPrice()));
        binding.price.setVisibility(booking.getPayment() != null ? View.GONE : View.VISIBLE);
        binding.dates.setText(binding.getRoot().getResources().getString(R.string.dates, booking.getCheckInDate(), booking.getCheckOutDate()));
        LocalDate today = LocalDate.now();
        if (booking.getCheckInDate().isAfter(today)) {
            binding.daysLeft.setText(binding.getRoot().getResources().getString(R.string.days_left, booking.getCheckInDate().toEpochDay() - today.toEpochDay()));
        }
        if (booking.getCheckInDate().isEqual(today)) {
            binding.daysLeft.setText(R.string.today);
        }
    }
}
