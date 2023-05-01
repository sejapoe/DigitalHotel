package ru.sejapoe.digitalhotel.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.sejapoe.digitalhotel.data.model.user.FriendRequests;
import ru.sejapoe.digitalhotel.data.model.user.User;
import ru.sejapoe.digitalhotel.data.model.user.UserInfo;
import ru.sejapoe.digitalhotel.data.model.user.UserLess;
import ru.sejapoe.digitalhotel.data.model.user.UserStatus;
import ru.sejapoe.digitalhotel.data.source.network.service.UserService;
import ru.sejapoe.digitalhotel.utils.LiveDataUtils;

@Singleton
public class UserRepository {
    private final UserService userService;

    @Inject
    public UserRepository(UserService userService) {
        this.userService = userService;
    }

    public void subscribe(String token) {
        try {
            userService.subscribe(token).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LiveData<UserStatus> userStatus() {
        return Transformations.map(LiveDataUtils.callToStatusLiveData(userService.ping()), integer -> {
            Log.d("Test", String.valueOf(integer));
            switch (integer) {
                case 401:
                    return UserStatus.NO_USER;
                case 403:
                    return UserStatus.NO_SURVEY;
                case 200:
                    return UserStatus.READY;
                default:
                    return UserStatus.NO_INTERNET;
            }
        });
    }

    public LiveData<Boolean> sendSurvey(String firstName, String lastName, String parentheses, String phoneNumber, LocalDate date, boolean isMale) {
        UserInfo userInfo = new UserInfo(0, firstName, lastName, parentheses, phoneNumber, date, isMale ? UserInfo.Sex.MALE : UserInfo.Sex.FEMALE);
        return LiveDataUtils.callToSuccessLiveData(userService.setInfo(userInfo));
    }

    public LiveData<User> getUser() {
        return LiveDataUtils.callToBodyLiveData(userService.getUser());
    }

    public LiveData<List<UserLess>> getFriends() {
        return LiveDataUtils.callToListLiveData(userService.getFriends());
    }

    public LiveData<FriendRequests> getFriendRequests() {
        return LiveDataUtils.callToBodyLiveData(userService.getFriendRequests(), new FriendRequests());
    }

    public LiveData<Integer> sendOrAcceptRequest(String targetUsername) {
        return LiveDataUtils.callToStatusLiveData(userService.sendOrAcceptRequest(targetUsername));
    }
}
