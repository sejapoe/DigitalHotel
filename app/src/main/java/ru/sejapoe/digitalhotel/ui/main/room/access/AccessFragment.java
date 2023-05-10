package ru.sejapoe.digitalhotel.ui.main.room.access;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import dagger.hilt.android.AndroidEntryPoint;
import ru.sejapoe.digitalhotel.R;
import ru.sejapoe.digitalhotel.data.model.hotel.access.Rights;
import ru.sejapoe.digitalhotel.data.model.hotel.access.RightsComposition;
import ru.sejapoe.digitalhotel.data.model.hotel.room.RoomAccess;
import ru.sejapoe.digitalhotel.databinding.FragmentAccessBinding;
import ru.sejapoe.digitalhotel.ui.main.room.RoomFragmentDirections;

@AndroidEntryPoint
public class AccessFragment extends Fragment {
    public static final String ARG_ACCESS = "access";
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
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AccessViewModel.class);

        Bundle args = requireArguments();
        RoomAccess roomAccess;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            roomAccess = args.getSerializable(ARG_ACCESS, RoomAccess.class);
        } else {
            roomAccess = (RoomAccess) args.getSerializable(ARG_ACCESS);
        }
        System.out.println(roomAccess);
        if (roomAccess == null) return;
        viewModel.setRoomId(roomAccess.getRoom().getId());

        RightsComposition rights = roomAccess.getRights();

        if (rights.satisfy(Rights.ACCESS_ROOM)) {
            binding.lock.setVisibility(View.VISIBLE);
            viewModel.isOpen().observe(getViewLifecycleOwner(), isOpen -> binding.lock.setImageResource(isOpen ? R.drawable.unlock_48 : R.drawable.lock_48));
            binding.lock.setOnClickListener(v -> viewModel.open());
        }

        if (rights.satisfy(Rights.MANAGE_ACCESS)) {
            binding.access.setVisibility(View.VISIBLE);
            RoomFragmentDirections.ActionRoomFragmentToAccessManagerFragment action = RoomFragmentDirections.actionRoomFragmentToAccessManagerFragment(roomAccess.getRoom().getId());
            binding.access.setOnClickListener(v -> NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(action));
        }
    }
}
