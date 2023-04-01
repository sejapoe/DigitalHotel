package ru.sejapoe.digitalhotel.ui.main.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.sejapoe.digitalhotel.databinding.FragmentBookingBinding;

public class BookingFragment extends Fragment {
    private FragmentBookingBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBookingBinding.inflate(inflater);
        return binding.getRoot();
    }
}
