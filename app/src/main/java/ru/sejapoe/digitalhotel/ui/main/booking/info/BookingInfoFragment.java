package ru.sejapoe.digitalhotel.ui.main.booking.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.time.LocalDate;

import dagger.hilt.android.AndroidEntryPoint;
import ru.sejapoe.digitalhotel.R;
import ru.sejapoe.digitalhotel.data.model.hotel.booking.Booking;
import ru.sejapoe.digitalhotel.databinding.FragmentBookingInfoBinding;
import ru.sejapoe.digitalhotel.ui.qr.QrCodeDialogFragment;
import ru.sejapoe.digitalhotel.utils.LocalDateAdapter;

@AndroidEntryPoint
public class BookingInfoFragment extends Fragment {
    private FragmentBookingInfoBinding binding;
    private BookingInfoViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBookingInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BookingInfoViewModel.class);
        int bookingId = requireArguments().getInt("booking_id");

        binding.qr.setOnClickListener(v -> {
            DialogFragment fragment = new QrCodeDialogFragment("https://www.sejapoe.live/booking/" + (bookingId * 71 + 13) + "/checkIn");
            fragment.show(getChildFragmentManager(), "QR");
            viewModel.isCheckedIn(bookingId).observe(getViewLifecycleOwner(), aBoolean -> {
                if (aBoolean) fragment.dismiss();
                NavHostFragment.findNavController(this).popBackStack();
            });
        });

        viewModel.getBooking(bookingId).observe(getViewLifecycleOwner(), booking -> {
            binding.hotelName.setText(booking.getHotel().getName());
            binding.roomType.setText(booking.getRoomType().getName());
            binding.checkInDate.setText(LocalDateAdapter.getDateString(booking.getCheckInDate()));
            binding.checkOutDate.setText(LocalDateAdapter.getDateString(booking.getCheckOutDate()));
            binding.actionButton.setEnabled(booking.getPayment() == null);
            setQrVisibility(booking);
        });

        binding.actionButton.setOnClickListener(v -> {
            binding.actionButton.setEnabled(false);
            viewModel.payBooking(bookingId).observe(getViewLifecycleOwner(), aBoolean -> {
                binding.actionButton.setEnabled(!aBoolean);
                viewModel.getBooking(bookingId).observe(getViewLifecycleOwner(), this::setQrVisibility);
            });
        });

        binding.cancelButton.setOnClickListener(v -> viewModel.deleteBooking(bookingId).observe(getViewLifecycleOwner(), isSuccess -> {
            if (isSuccess) {
                NavHostFragment.findNavController(this).navigate(R.id.action_bookingInfoFragment_to_bookingFragment);
            }
        }));
    }

    private void setQrVisibility(@NonNull Booking booking) {
        binding.qr.setVisibility(booking.getCheckInDate().toEpochDay() <= LocalDate.now().toEpochDay() && booking.getPayment() != null ? View.VISIBLE : View.GONE);
    }
}
