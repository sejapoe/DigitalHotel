package ru.sejapoe.digitalhotel.ui.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import ru.sejapoe.digitalhotel.databinding.ActivityLoginBinding;
import ru.sejapoe.digitalhotel.ui.viewmodel.LoginViewModel;

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

        binding.login.setOnClickListener(view -> loginViewModel.register(
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
        });

        loginViewModel.getAuthStateMutableLiveData().observe(this, authState -> Toast.makeText(this, authState.toString(), Toast.LENGTH_LONG).show());

        binding.helloBtn.setOnClickListener(view -> loginViewModel.hello());

        validateForm();

        setContentView(binding.getRoot());
    }

    private void validateForm() {
        loginViewModel.validateForm(
                binding.username.getText().toString(),
                binding.password.getText().toString()
        );
    }
}
