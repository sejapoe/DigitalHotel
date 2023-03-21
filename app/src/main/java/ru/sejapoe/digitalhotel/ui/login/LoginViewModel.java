package ru.sejapoe.digitalhotel.ui.login;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.regex.Pattern;

import ru.sejapoe.digitalhotel.R;
import ru.sejapoe.digitalhotel.data.db.AppDatabase;
import ru.sejapoe.digitalhotel.data.model.LoginFormState;
import ru.sejapoe.digitalhotel.data.repository.LoginRepository;

public class LoginViewModel extends AndroidViewModel {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])");
    private final LoginRepository loginRepository;
    private LoginFormState formState;
    private final MutableLiveData<LoginFormState> loginFormStateMutableLiveData = new MutableLiveData<>(formState);
    private LoginFormState.AuthState authState = LoginFormState.AuthState.LOGIN;
    private final MutableLiveData<LoginFormState.AuthState> authStateMutableLiveData = new MutableLiveData<>(authState);

    public LoginViewModel(Application application) {
        super(application);
        loginRepository = new LoginRepository(AppDatabase.getInstance(application).sessionDao());
    }

    public void validateForm(String username, String password, String repeatedPassword) {
        setFormState(
                new LoginFormState(
                        EMAIL_PATTERN.matcher(username).matches() ? android.R.string.ok : R.string.invalid_username,
                        password.length() > 5 ? android.R.string.ok : R.string.invalid_password,
                        (authState != LoginFormState.AuthState.REGISTER || password.equals(repeatedPassword))
                                ? android.R.string.ok : R.string.passwords_don_t_match
                )
        );
    }

    public void loginOrRegister(String username, String password) {
        if (authState == LoginFormState.AuthState.REGISTER) register(username, password);
        else login(username, password);
    }

    public void login(String username, String password) {
        setAuthState(LoginFormState.AuthState.WAITING);
        new Thread(() -> {
            try {
                loginRepository.login(username, password);
                setAuthState(LoginFormState.AuthState.FINE);
            } catch (LoginRepository.NoSuchUserException e) {
                setAuthState(LoginFormState.AuthState.REGISTER);
            } catch (LoginRepository.WrongPasswordException e) {
                setAuthState(LoginFormState.AuthState.WRONG_PASSWORD);
            } catch (GeneralSecurityException | IOException e) {
                setAuthState(LoginFormState.AuthState.INTERNAL_ERROR);
            }
        }).start();
    }

    public void register(String username, String password) {
        setAuthState(LoginFormState.AuthState.WAITING);
        new Thread(() -> {
            try {
                loginRepository.register(username, password);
                loginRepository.login(username, password);
                setAuthState(LoginFormState.AuthState.FINE);
            } catch (LoginRepository.UserAlreadyExists e) {
                setAuthState(LoginFormState.AuthState.LOGIN);
            } catch (GeneralSecurityException | IOException e) {
                setAuthState(LoginFormState.AuthState.INTERNAL_ERROR);
            } catch (LoginRepository.WrongPasswordException |
                     LoginRepository.NoSuchUserException e) {
                setAuthState(LoginFormState.AuthState.INTERNAL_ERROR); // unreachable
            }
        }).start();
    }

    private void setFormState(LoginFormState formState) {
        this.formState = formState;
        loginFormStateMutableLiveData.postValue(this.formState);
    }

    public void setAuthState(LoginFormState.AuthState authState) {
        this.authState = authState;
        authStateMutableLiveData.postValue(this.authState);
    }

    public LiveData<LoginFormState> getLoginFormStateMutableLiveData() {
        return loginFormStateMutableLiveData;
    }

    public LiveData<LoginFormState.AuthState> getAuthStateMutableLiveData() {
        return authStateMutableLiveData;
    }
}
