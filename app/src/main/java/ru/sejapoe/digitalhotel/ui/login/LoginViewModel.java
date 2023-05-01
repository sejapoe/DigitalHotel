package ru.sejapoe.digitalhotel.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.regex.Pattern;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ru.sejapoe.digitalhotel.R;
import ru.sejapoe.digitalhotel.data.model.login.LoginFormState;
import ru.sejapoe.digitalhotel.data.repository.LoginRepository;

@HiltViewModel
public class LoginViewModel extends ViewModel {
    public static final Pattern EMAIL_PATTERN = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])");
    private final LoginRepository loginRepository;
    private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>(new LoginFormState());
    private final MutableLiveData<LoginFormState.AuthState> authState = new MutableLiveData<>(LoginFormState.AuthState.LOGIN);

    @Inject
    public LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public void validateForm(String username, String password, String repeatedPassword) {
        loginFormState.postValue(
                new LoginFormState(
                        EMAIL_PATTERN.matcher(username).matches() ? android.R.string.ok : R.string.invalid_username,
                        password.length() > 5 ? android.R.string.ok : R.string.invalid_password,
                        (authState.getValue() != LoginFormState.AuthState.REGISTER || password.equals(repeatedPassword))
                                ? android.R.string.ok : R.string.passwords_don_t_match
                )
        );
    }

    public void loginOrRegister(String username, String password) {
        if (authState.getValue() == LoginFormState.AuthState.REGISTER) register(username, password);
        else login(username, password);
    }

    public void login(String username, String password) {
        authState.postValue(LoginFormState.AuthState.WAITING);
        new Thread(() -> {
            try {
                loginRepository.login(username, password);
                authState.postValue(LoginFormState.AuthState.FINE);
            } catch (LoginRepository.NoSuchUserException e) {
                authState.postValue(LoginFormState.AuthState.REGISTER);
            } catch (LoginRepository.WrongPasswordException e) {
                authState.postValue(LoginFormState.AuthState.WRONG_PASSWORD);
            } catch (GeneralSecurityException | IOException e) {
                authState.postValue(LoginFormState.AuthState.INTERNAL_ERROR);
            }
        }).start();
    }

    public void register(String username, String password) {
        authState.postValue(LoginFormState.AuthState.WAITING);
        new Thread(() -> {
            try {
                loginRepository.register(username, password);
                loginRepository.login(username, password);
                authState.postValue(LoginFormState.AuthState.FINE);
            } catch (LoginRepository.UserAlreadyExists e) {
                authState.postValue(LoginFormState.AuthState.LOGIN);
            } catch (GeneralSecurityException | IOException e) {
                authState.postValue(LoginFormState.AuthState.INTERNAL_ERROR);
            } catch (LoginRepository.WrongPasswordException |
                     LoginRepository.NoSuchUserException e) {
                authState.postValue(LoginFormState.AuthState.INTERNAL_ERROR); // unreachable
            }
        }).start();
    }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public LiveData<LoginFormState.AuthState> getAuthState() {
        return authState;
    }
}
