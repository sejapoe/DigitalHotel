package ru.sejapoe.digitalhotel.ui.main.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import ru.sejapoe.digitalhotel.R;
import ru.sejapoe.digitalhotel.databinding.FragmentBookingBinding;
import ru.sejapoe.digitalhotel.ui.main.booking.guestcount.GuestCountDialogFragment;

public class BookingFragment extends Fragment {
    private FragmentBookingBinding binding;
    private BookingViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BookingViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBookingBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.guestsCount.setOnClickListener(v -> {
            GuestCountDialogFragment guestCountDialogFragment = new GuestCountDialogFragment(Objects.requireNonNull(viewModel.getGuestsCount().getValue()));
            guestCountDialogFragment.setGuestCountListener(viewModel::setGuestsCount);
            guestCountDialogFragment.show(getChildFragmentManager(), "guest_count");
        });

        View.OnClickListener listener = v -> {
            MaterialDatePicker<Pair<Long, Long>> dateRangePicker = MaterialDatePicker.Builder
                    .dateRangePicker()
                    .setTitleText(R.string.select_dates)
                    .setCalendarConstraints(
                            new CalendarConstraints.Builder()
                                    .setStart(MaterialDatePicker.todayInUtcMilliseconds())
                                    .setOpenAt(MaterialDatePicker.todayInUtcMilliseconds())
                                    .setEnd(MaterialDatePicker.todayInUtcMilliseconds() + (long) 365 * 24 * 60 * 60 * 1000)
                                    .setValidator(DateValidatorPointForward.now())
                                    .build()
                    )
                    .setSelection(Objects.requireNonNull(viewModel.getBookingDates().getValue()).asMillisPair())
                    .build();
            dateRangePicker.addOnPositiveButtonClickListener(viewModel::setBookingDates);
            dateRangePicker.show(getChildFragmentManager(), "booking_dates");
        };

        binding.checkInDate.setOnClickListener(listener);
        binding.checkOutDate.setOnClickListener(listener);

        viewModel.getBookingDates().observe(this.getViewLifecycleOwner(), bookingDates -> {
            binding.checkInDate.setText(getDateString(bookingDates.getCheckIn()));
            binding.checkOutDate.setText(getDateString(bookingDates.getCheckOut()));
        });

        viewModel.getGuestsCount().observe(this.getViewLifecycleOwner(), guestsCount -> {
            int adultCount = guestsCount.getAdultsCount();
            int childrenCount = guestsCount.getChildrenCount();
            String text = getResources().getQuantityString(R.plurals.adults_count, adultCount, adultCount);
            if (childrenCount > 0) {
                text += ", " + getResources().getQuantityString(R.plurals.children_count, childrenCount, childrenCount);
            }
            binding.guestsCount.setText(text);
        });
    }

    private static String getDateString(LocalDate localDate) {
        return DateTimeFormatter.ofPattern("MMMM d, EEEE").format(localDate);
    }
}
