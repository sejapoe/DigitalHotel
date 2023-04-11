package ru.sejapoe.digitalhotel.ui.main.room;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import dagger.hilt.android.AndroidEntryPoint;
import ru.sejapoe.digitalhotel.databinding.FragmentRoomBinding;

@AndroidEntryPoint
public class RoomFragment extends Fragment {
    private FragmentRoomBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRoomBinding.inflate(inflater);
        return binding.getRoot();
    }
}
