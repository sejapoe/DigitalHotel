package ru.sejapoe.digitalhotel.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.sejapoe.digitalhotel.R;
import ru.sejapoe.digitalhotel.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private FragmentMainBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.getIsLoggedMutableLiveData().observe(this, isLogged -> {
            if (!isLogged) {
                NavHostFragment.findNavController(this).navigate(R.id.action_mainFragment_to_loadingFragment);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.main_nav_host_fragment);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            BottomNavigationView navigationBar = binding.bottomNavigationBar;
            NavigationUI.setupWithNavController(navigationBar, navController);
        }
//        binding.buttonLogOut.setOnClickListener(v -> mViewModel.logOut());
    }
}