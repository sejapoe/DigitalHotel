package ru.sejapoe.digitalhotel.ui.main.room.access;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;
import ru.sejapoe.digitalhotel.databinding.FragmentAccessBinding;

@AndroidEntryPoint
public class AccessFragment extends Fragment {
    private FragmentAccessBinding binding;
    private AccessViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccessBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AccessViewModel.class);
        int occupationId = AccessFragmentArgs.fromBundle(requireArguments()).getOccupationId();
        viewModel.setOccupationId(occupationId);
    }
}
