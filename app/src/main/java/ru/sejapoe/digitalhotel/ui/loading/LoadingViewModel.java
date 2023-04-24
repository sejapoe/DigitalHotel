package ru.sejapoe.digitalhotel.ui.loading;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ru.sejapoe.digitalhotel.data.model.login.UserStatus;
import ru.sejapoe.digitalhotel.data.repository.LoginRepository;

@HiltViewModel
public class LoadingViewModel extends ViewModel {
    private final LoginRepository loginRepository;

    @Inject
    public LoadingViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public LiveData<UserStatus> userStatus() {
        return loginRepository.userStatus();
    }
}
