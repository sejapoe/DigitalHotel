package ru.sejapoe.digitalhotel.ui.main.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import ru.sejapoe.digitalhotel.R;
import ru.sejapoe.digitalhotel.data.model.hotel.BookableRoom;
import ru.sejapoe.digitalhotel.data.model.hotel.HotelLess;
import ru.sejapoe.digitalhotel.databinding.FragmentBookingBinding;
import ru.sejapoe.digitalhotel.ui.main.booking.guestcount.GuestCountDialogFragment;

@AndroidEntryPoint
public class BookingFragment extends Fragment {
    private FragmentBookingBinding binding;
    private BookingViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBookingBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BookingViewModel.class);

        viewModel.getHotels().observe(getViewLifecycleOwner(), hotels -> {
            ArrayAdapter<HotelLess> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, hotels);
            binding.hotelSpinner.setAdapter(adapter);
        });

        binding.hotelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HotelLess hotel = (HotelLess) parent.getItemAtPosition(position);
                viewModel.setHotelId(hotel.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                viewModel.setHotelId(null);
            }
        });

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

        binding.searchButton.setOnClickListener(v -> viewModel.search());

        viewModel.getBookableRooms().observe(getViewLifecycleOwner(), bookableRooms -> {
            if (bookableRooms == null) return;
            if (bookableRooms.isEmpty()) {
                Toast.makeText(requireContext(), R.string.no_rooms_found, Toast.LENGTH_SHORT).show();
                return;
            }
            Bundle args = new Bundle();
            args.putParcelableArray("bookableRooms", bookableRooms.toArray(new BookableRoom[0]));
            BookingViewModel.BookingDates value = viewModel.getBookingDates().getValue();
            Integer hotelId = viewModel.getHotelId().getValue();
            if (value == null || hotelId == null) return;

            args.putString("checkIn", value.getCheckIn().toString());
            args.putString("checkOut", value.getCheckOut().toString());
            args.putInt("hotelId", hotelId);

            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.bookableRoomsFragment, args);
        });
    }

    private static String getDateString(LocalDate localDate) {
        return DateTimeFormatter.ofPattern("MMMM d, EEEE").format(localDate);
    }
}
