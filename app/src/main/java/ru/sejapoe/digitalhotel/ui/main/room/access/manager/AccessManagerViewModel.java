package ru.sejapoe.digitalhotel.ui.main.room.access.manager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ru.sejapoe.digitalhotel.data.model.hotel.access.AccessShare;
import ru.sejapoe.digitalhotel.data.model.user.UserLess;
import ru.sejapoe.digitalhotel.data.repository.AccessRepository;
import ru.sejapoe.digitalhotel.data.repository.UserRepository;
import ru.sejapoe.digitalhotel.utils.LiveDataUtils;

@HiltViewModel
public class AccessManagerViewModel extends ViewModel {
    private int roomId;
    private final AccessRepository accessRepository;
    private final UserRepository userRepository;

    @Inject
    public AccessManagerViewModel(AccessRepository accessRepository, UserRepository userRepository) {
        this.accessRepository = accessRepository;
        this.userRepository = userRepository;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public LiveData<List<AccessShare>> getSharedAccesses() {
        return accessRepository.getSharedAccesses(roomId);
    }

    public LiveData<List<UserLess>> getFriends() {
        return LiveDataUtils.combine(getSharedAccesses(), userRepository.getFriends(), (accessShares, friends) -> friends.stream().filter(userLess -> accessShares.stream().noneMatch(accessShare -> accessShare.getUser().getId() == userLess.getId())).collect(Collectors.toList()));
    }

    public LiveData<Boolean> shareAccess(List<String> usernames) {
        return accessRepository.shareAccess(roomId, usernames);
    }
}
