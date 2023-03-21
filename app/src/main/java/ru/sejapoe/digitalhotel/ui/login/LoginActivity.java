package ru.sejapoe.digitalhotel.ui.login;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import ru.sejapoe.digitalhotel.data.model.LoginFormState;
import ru.sejapoe.digitalhotel.databinding.ActivityLoginBinding;
import ru.sejapoe.digitalhotel.ui.main.MainActivity;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    LoginViewModel loginViewModel;

    @Override
    protected void onStart() {
        super.onStart();
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
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

        binding.login.setOnClickListener(view -> loginViewModel.loginOrRegister(
                binding.username.getText().toString(),
                binding.password.getText().toString()
        ));

        loginViewModel.getLoginFormStateMutableLiveData().observe(this, loginFormState -> {
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

        loginViewModel.getAuthStateMutableLiveData().observe(this, authState -> {
            binding.login.setEnabled(authState != LoginFormState.AuthState.WAITING);
            Log.d("DigitalHotelApplication", authState.toString());
            switch (authState) {
                case LOGIN:
                    binding.repeatPassword.setVisibility(View.GONE);
                    binding.login.setEnabled(true);
                    break;
                case REGISTER:
                    binding.repeatPassword.setVisibility(View.VISIBLE);
                    binding.login.setEnabled(false);
                    break;
                case WRONG_PASSWORD:
                case INTERNAL_ERROR:
                    Toast.makeText(this, authState.toString(), Toast.LENGTH_LONG).show();
                    break;
                case FINE:
                    switchToMainActivity();
                    break;
            }
        });

        validateForm();

        setContentView(binding.getRoot());
    }

    private void switchToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void validateForm() {
        loginViewModel.validateForm(
                binding.username.getText().toString(),
                binding.password.getText().toString(),
                binding.repeatPassword.getText().toString()
        );
    }
}
