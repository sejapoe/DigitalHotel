package ru.sejapoe.digitalhotel.ui.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import ru.sejapoe.digitalhotel.R;
import ru.sejapoe.digitalhotel.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        viewModel.isLogged().observe(this, isLogged -> {
            if (!isLogged) {
                assert getParentFragment() != null;
                Fragment frag = getParentFragment().getParentFragment();
                assert frag != null;
                NavHostFragment.findNavController(frag).navigate(R.id.action_mainFragment_to_loadingFragment);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater);
        binding.logoutBtn.setOnClickListener(v -> viewModel.logOut());
        return binding.getRoot();
    }
}
