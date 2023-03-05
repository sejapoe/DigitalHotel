package ru.sejapoe.digitalhotel.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import okhttp3.Response;
import ru.sejapoe.digitalhotel.R;
import ru.sejapoe.digitalhotel.data.LoginFormState;
import ru.sejapoe.digitalhotel.data.auth.AuthState;
import ru.sejapoe.digitalhotel.data.auth.BitArray256;
import ru.sejapoe.digitalhotel.data.auth.LoginRepository;
import ru.sejapoe.digitalhotel.data.net.HttpProvider;

public class LoginViewModel extends ViewModel {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])");
    private final LoginRepository loginRepository = new LoginRepository();
    private LoginFormState formState;
    private final MutableLiveData<LoginFormState> loginFormStateMutableLiveData = new MutableLiveData<>(formState);
    private AuthState authState = AuthState.NOTHING;
    private final MutableLiveData<AuthState> authStateMutableLiveData = new MutableLiveData<>(authState);

    public void validateForm(String username, String password) {
        setFormState(
                new LoginFormState(
                        EMAIL_PATTERN.matcher(username).matches() ? android.R.string.ok : R.string.invalid_username,
                        password.length() > 5 ? android.R.string.ok : R.string.invalid_password
                )
        );
    }

    public void register(String username, String password) {
        new Thread(() -> {
            try {
                loginRepository.register(username, password);
                loginRepository.login(username, password);
                setAuthState(AuthState.FINE);
            } catch (LoginRepository.UserAlreadyExists e) {
                try {
                    loginRepository.login(username, password);
                    setAuthState(AuthState.FINE);
                } catch (LoginRepository.WrongPasswordException ex) {
                    setAuthState(AuthState.WRONG_PASSWORD);
                } catch (IOException | GeneralSecurityException ex) {
                    setAuthState(AuthState.INTERNAL_ERROR);
                }
            } catch (GeneralSecurityException | IOException e) {
                setAuthState(AuthState.INTERNAL_ERROR);
            } catch (LoginRepository.WrongPasswordException e) {
                setAuthState(AuthState.WRONG_PASSWORD); // unreachable
            }
        }).start();
    }

    public void hello() {
        new Thread(() -> {
            try {
                loginRepository.getHttpProvider().postAuth("http://192.168.0.15:8080/hello", new byte[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public LoginFormState getFormState() {
        return formState;
    }

    private void setFormState(LoginFormState formState) {
        this.formState = formState;
        loginFormStateMutableLiveData.postValue(this.formState);
    }

    public AuthState getAuthState() {
        return authState;
    }

    public void setAuthState(AuthState authState) {
        this.authState = authState;
        authStateMutableLiveData.postValue(this.authState);
    }

    public LiveData<LoginFormState> getLoginFormStateMutableLiveData() {
        return loginFormStateMutableLiveData;
    }

    public LiveData<AuthState> getAuthStateMutableLiveData() {
        return authStateMutableLiveData;
    }
}
