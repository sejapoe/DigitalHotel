package ru.sejapoe.digitalhotel.ui.main.room;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayoutMediator;

import dagger.hilt.android.AndroidEntryPoint;
import ru.sejapoe.digitalhotel.data.model.hotel.room.Occupation;
import ru.sejapoe.digitalhotel.databinding.FragmentRoomBinding;

@AndroidEntryPoint
public class RoomFragment extends Fragment {
    private FragmentRoomBinding binding;
    private RoomViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRoomBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RoomViewModel.class);
        binding.pager.setAdapter(new RoomPagerAdapter(this));
        new TabLayoutMediator(binding.tabLayout, binding.pager, (tab, position) -> {
            Occupation occupation = ((RoomPagerAdapter) binding.pager.getAdapter()).getItem(position);
            tab.setText(occupation.getRoom().getHotel().getName() + ":" + occupation.getRoom().getNumber());
        }).attach();
        viewModel.getOccupations().observe(getViewLifecycleOwner(), occupations -> ((RoomPagerAdapter) binding.pager.getAdapter()).setOccupations(occupations));
    }
}
