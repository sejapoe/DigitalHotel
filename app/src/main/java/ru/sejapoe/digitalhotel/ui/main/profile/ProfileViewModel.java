package ru.sejapoe.digitalhotel.ui.main.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ru.sejapoe.digitalhotel.data.model.user.User;
import ru.sejapoe.digitalhotel.data.repository.LoginRepository;
import ru.sejapoe.digitalhotel.data.repository.UserRepository;

@HiltViewModel
public class ProfileViewModel extends ViewModel {
    private final LoginRepository loginRepository;
    private final UserRepository userRepository;
    private final MutableLiveData<Boolean> isLogged = new MutableLiveData<>(true);

    @Inject
    public ProfileViewModel(LoginRepository loginRepository, UserRepository userRepository) {
        this.loginRepository = loginRepository;
        this.userRepository = userRepository;
    }

    public LiveData<User> getUser() {
        return userRepository.getUser();
    }

    public void logOut() {
        new Thread(() -> {
            loginRepository.logOut();
            isLogged.postValue(false);
        }).start();
    }

    public LiveData<Boolean> isLogged() {
        return isLogged;
    }
}