package ru.sejapoe.digitalhotel.ui.main.room.access;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import dagger.hilt.android.AndroidEntryPoint;
import ru.sejapoe.digitalhotel.databinding.FragmentAccessBinding;

@AndroidEntryPoint
public class AccessFragment extends Fragment {
    FragmentAccessBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccessBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
