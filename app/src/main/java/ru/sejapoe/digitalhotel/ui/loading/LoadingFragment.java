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

import ru.sejapoe.digitalhotel.R;
import ru.sejapoe.digitalhotel.databinding.FragmentLoadingBinding;

public class LoadingFragment extends Fragment {
    private LoadingViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(LoadingViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentLoadingBinding binding = FragmentLoadingBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.load();
        viewModel.getIsLoggedMutableLiveData().observe(getViewLifecycleOwner(), isLogged -> {
            if (isLogged != null)
                NavHostFragment.findNavController(this).navigate(
                        isLogged ? R.id.action_loadingFragment_to_mainFragment
                                : R.id.action_loadingFragment_to_loginFragment
                );
        });
    }
}