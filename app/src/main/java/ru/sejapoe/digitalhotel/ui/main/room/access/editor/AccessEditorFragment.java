package ru.sejapoe.digitalhotel.ui.main.room.access.editor;

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
import ru.sejapoe.digitalhotel.data.model.hotel.access.Rights;
import ru.sejapoe.digitalhotel.data.model.hotel.access.RightsComposition;
import ru.sejapoe.digitalhotel.databinding.FragmentAccessEditorBinding;

@AndroidEntryPoint
public class AccessEditorFragment extends Fragment {
    private FragmentAccessEditorBinding binding;
    private AccessEditorViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccessEditorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AccessEditorViewModel.class);
        viewModel.setAccessId(AccessEditorFragmentArgs.fromBundle(requireArguments()).getAccessId());
        viewModel.getShare().observe(getViewLifecycleOwner(), accessShare -> {
            RightsComposition rights = accessShare.getRights();
            System.out.println(rights.getValue());
            binding.rightAccessCheckbox.setChecked(rights.satisfy(Rights.ACCESS_ROOM));
            binding.rightManageRoomCheckbox.setChecked(rights.satisfy(Rights.MANAGE_ROOM));
            binding.rightReservationsCheckbox.setChecked(rights.satisfy(Rights.MANAGE_RESERVATIONS));
            binding.rightPayCheckbox.setChecked(rights.satisfy(Rights.PAY_FOR_SERVICES));
        });
        binding.saveBtn.setOnClickListener(v -> {
            RightsComposition rightsComposition = new RightsComposition(
                    (binding.rightAccessCheckbox.isChecked() ? Rights.ACCESS_ROOM.getValue() : 0) |
                            (binding.rightManageRoomCheckbox.isChecked() ? Rights.MANAGE_ROOM.getValue() : 0) |
                            (binding.rightReservationsCheckbox.isChecked() ? Rights.MANAGE_RESERVATIONS.getValue() : 0) |
                            (binding.rightPayCheckbox.isChecked() ? Rights.PAY_FOR_SERVICES.getValue() : 0)
            );
            viewModel.save(rightsComposition, -1).observe(getViewLifecycleOwner(), isSuccess -> {
//                if (isSuccess) NavHostFragment.findNavController(this).navigate(R.id.accessManagerFragment);
                if (isSuccess) NavHostFragment.findNavController(this).popBackStack();
            });
        });
    }
}
