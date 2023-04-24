package ru.sejapoe.digitalhotel.ui.loading;

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
import ru.sejapoe.digitalhotel.databinding.FragmentLoadingBinding;

@AndroidEntryPoint
public class LoadingFragment extends Fragment {
    private LoadingViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentLoadingBinding binding = FragmentLoadingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LoadingViewModel.class);
        viewModel.userStatus().observe(getViewLifecycleOwner(), status -> {
            switch (status) {
                case NO_INTERNET:
                    break;
                case NO_USER:
                    NavHostFragment.findNavController(this).navigate(R.id.action_loadingFragment_to_loginFragment);
                    break;
                case NO_SURVEY:
                    NavHostFragment.findNavController(this).navigate(R.id.action_loadingFragment_to_surveyFragment);
                    break;
                case READY:
                    NavHostFragment.findNavController(this).navigate(R.id.action_loadingFragment_to_mainFragment);
                    break;
            }
        });
    }
}