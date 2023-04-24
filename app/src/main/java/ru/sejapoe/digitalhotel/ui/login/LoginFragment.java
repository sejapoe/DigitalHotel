package ru.sejapoe.digitalhotel.ui.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import dagger.hilt.android.AndroidEntryPoint;
import ru.sejapoe.digitalhotel.R;
import ru.sejapoe.digitalhotel.data.model.login.LoginFormState;
import ru.sejapoe.digitalhotel.databinding.FragmentLoginBinding;

@AndroidEntryPoint
public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    LoginViewModel loginViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
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

        loginViewModel.getLoginFormState().observe(getViewLifecycleOwner(), loginFormState -> {
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

        final SpringAnimation animation = new SpringAnimation(binding.repeatPassword, DynamicAnimation.TRANSLATION_Y, 0);
        animation.setStartValue(-48);

        loginViewModel.getAuthState().observe(getViewLifecycleOwner(), authState -> {
            binding.login.setEnabled(authState != LoginFormState.AuthState.WAITING);
            Log.d("DigitalHotelApplication", authState.toString());
            switch (authState) {
                case LOGIN:
                    binding.username.setFreezesText(false);
                    break;
                case REGISTER:
                    binding.username.setEnabled(false);
                    binding.login.setEnabled(false);
                    binding.repeatPassword.setVisibility(View.VISIBLE);
                    animation.start();
                    break;
                case WRONG_PASSWORD:
                case INTERNAL_ERROR:
                    Toast.makeText(getContext(), authState.toString(), Toast.LENGTH_LONG).show();
                    break;
                case FINE:
                    NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_loadingFragment);
                    break;
            }
        });

        validateForm();
    }

    private void validateForm() {
        loginViewModel.validateForm(
                binding.username.getText().toString(),
                binding.password.getText().toString(),
                binding.repeatPassword.getText().toString()
        );
    }
}
