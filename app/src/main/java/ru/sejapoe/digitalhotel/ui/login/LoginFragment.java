package ru.sejapoe.digitalhotel.ui.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import ru.sejapoe.digitalhotel.R;
import ru.sejapoe.digitalhotel.data.model.LoginFormState;
import ru.sejapoe.digitalhotel.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    LoginViewModel loginViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fall_out);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                validateForm();
            }
        };

        binding.username.addTextChangedListener(textWatcher);
        binding.password.addTextChangedListener(textWatcher);
        binding.repeatPassword.addTextChangedListener(textWatcher);

        binding.login.setOnClickListener(v -> loginViewModel.loginOrRegister(
                binding.username.getText().toString(),
                binding.password.getText().toString()
        ));

        loginViewModel.getLoginFormStateMutableLiveData().observe(getViewLifecycleOwner(), loginFormState -> {
            if (loginFormState == null) {
                binding.login.setEnabled(false);
                return;
            }


            binding.login.setEnabled(loginFormState.isOk());

            if (loginFormState.hasUsernameError() && !binding.username.getText().toString().isEmpty()) {
                binding.username.setError(getString(loginFormState.getUsernameError()));
            }

            if (loginFormState.hasPasswordError() && !binding.password.getText().toString().isEmpty()) {
                binding.password.setError(getString(loginFormState.getPasswordError()));
            }

            if (loginFormState.hasRepeatedPasswordError() && !binding.password.getText().toString().isEmpty()) {
                binding.repeatPassword.setError(getString(loginFormState.getRepeatedPasswordError()));
            }
        });

        loginViewModel.getAuthStateMutableLiveData().observe(getViewLifecycleOwner(), authState -> {
            binding.login.setEnabled(authState != LoginFormState.AuthState.WAITING);
            Log.d("DigitalHotelApplication", authState.toString());
            switch (authState) {
                case LOGIN:
//                    binding.repeatPassword.setVisibility(View.GONE);
                    binding.login.setEnabled(true);
                    break;
                case REGISTER:
                    binding.repeatPassword.startAnimation(animation);
//                    binding.repeatPassword.setVisibility(View.VISIBLE);
                    binding.login.setEnabled(false);
                    break;
                case WRONG_PASSWORD:
                case INTERNAL_ERROR:
                    Toast.makeText(getContext(), authState.toString(), Toast.LENGTH_LONG).show();
                    break;
                case FINE:
                    switchToMainActivity();
                    break;
            }
        });

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.repeatPassword.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        validateForm();
    }

    private void switchToMainActivity() {
        NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_mainFragment);
    }

    private void validateForm() {
        loginViewModel.validateForm(
                binding.username.getText().toString(),
                binding.password.getText().toString(),
                binding.repeatPassword.getText().toString()
        );
    }
}
