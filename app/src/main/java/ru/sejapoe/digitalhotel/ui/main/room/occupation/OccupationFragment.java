package ru.sejapoe.digitalhotel.ui.main.room.occupation;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;
import ru.sejapoe.digitalhotel.R;
import ru.sejapoe.digitalhotel.data.model.hotel.Occupation;
import ru.sejapoe.digitalhotel.databinding.FragmentOccupationBinding;

@AndroidEntryPoint
public class OccupationFragment extends Fragment {
    public static final String ARG_OCCUPATION = "occupation";
    private Occupation occupation;
    private FragmentOccupationBinding binding;
    private OccupationViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOccupationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(OccupationViewModel.class);

        Bundle args = requireArguments();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            occupation = args.getSerializable(ARG_OCCUPATION, Occupation.class);
        } else {
            occupation = (Occupation) args.getSerializable(ARG_OCCUPATION);
        }
        if (occupation == null) return;
        viewModel.setRoomId(occupation.getRoom().getId());

        viewModel.isOpen().observe(getViewLifecycleOwner(), isOpen -> binding.lock.setImageResource(isOpen ? R.drawable.unlock_48 : R.drawable.lock_48));

        binding.lock.setOnClickListener(v -> viewModel.open());
    }
}
