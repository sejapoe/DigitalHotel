package ru.sejapoe.digitalhotel.ui.main.profile.friends;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ru.sejapoe.digitalhotel.data.model.user.FriendRequests;
import ru.sejapoe.digitalhotel.data.model.user.UserLess;
import ru.sejapoe.digitalhotel.data.repository.UserRepository;

@HiltViewModel
public class FriendViewModel extends ViewModel {
    private final UserRepository userRepository;

    @Inject
    public FriendViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<List<UserLess>> getFriends() {
        return userRepository.getFriends();
    }

    public LiveData<FriendRequests> getFriendRequests() {
        return userRepository.getFriendRequests();
    }

    public LiveData<Integer> sendOrAcceptRequest(String targetUsername) {
        return userRepository.sendOrAcceptRequest(targetUsername);
    }
}
