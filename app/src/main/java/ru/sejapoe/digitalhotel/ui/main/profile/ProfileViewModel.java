package ru.sejapoe.digitalhotel.ui.main.profile;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import ru.sejapoe.digitalhotel.data.repository.LoginRepository;
import ru.sejapoe.digitalhotel.data.source.db.AppDatabase;

public class ProfileViewModel extends AndroidViewModel {
    private final LoginRepository loginRepository;
    private final MutableLiveData<Boolean> isLogged = new MutableLiveData<>(true);

    public ProfileViewModel(Application application) {
        super(application);
        loginRepository = new LoginRepository(AppDatabase.getInstance(application).sessionDao());
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