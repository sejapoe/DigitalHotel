package ru.sejapoe.digitalhotel.ui.main.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import ru.sejapoe.digitalhotel.R;
import ru.sejapoe.digitalhotel.data.model.hotel.booking.Booking;
import ru.sejapoe.digitalhotel.databinding.FragmentBookingBinding;
import ru.sejapoe.digitalhotel.ui.core.ClickableItemListAdapter;
import ru.sejapoe.digitalhotel.ui.core.ItemPaddingDecorator;

@AndroidEntryPoint
public class BookingFragment extends Fragment {
    private FragmentBookingBinding binding;
    private BookingViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentBookingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(BookingViewModel.class);

        binding.addFab.setOnClickListener(v -> NavHostFragment.findNavController(this).navigate(R.id.action_bookingFragment_to_bookingCreateFragment));

        ClickableItemListAdapter.OnItemClickListener<Booking> listener = booking -> {
            Bundle args = new Bundle();
            args.putInt("booking_id", booking.getId());
            NavHostFragment.findNavController(this).navigate(R.id.action_bookingFragment_to_bookingInfoFragment, args);
        };

        binding.bookingsRecyclerView.setAdapter(new BookingsAdapter(listener));
        binding.bookingsRecyclerView.addItemDecoration(new ItemPaddingDecorator(getResources().getDimensionPixelSize(R.dimen.item_padding)));
        viewModel.getBookings().observe(getViewLifecycleOwner(), bookings -> ((BookingsAdapter) Objects.requireNonNull(binding.bookingsRecyclerView.getAdapter())).setItems(bookings));
    }
}