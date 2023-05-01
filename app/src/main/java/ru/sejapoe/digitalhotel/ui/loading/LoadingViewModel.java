package ru.sejapoe.digitalhotel.ui.loading;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ru.sejapoe.digitalhotel.data.model.user.UserStatus;
import ru.sejapoe.digitalhotel.data.repository.UserRepository;

@HiltViewModel
public class LoadingViewModel extends ViewModel {
    private final UserRepository userRepository;

    @Inject
    public LoadingViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<UserStatus> userStatus() {
        return userRepository.userStatus();
    }
}
