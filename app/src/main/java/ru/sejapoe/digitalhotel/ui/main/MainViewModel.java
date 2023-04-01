package ru.sejapoe.digitalhotel.ui.main;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import ru.sejapoe.digitalhotel.data.db.AppDatabase;
import ru.sejapoe.digitalhotel.data.repository.LoginRepository;

public class MainViewModel extends AndroidViewModel {
    private final LoginRepository loginRepository;
    private boolean isLogged = true;
    private final MutableLiveData<Boolean> isLoggedMutableLiveData = new MutableLiveData<>(true);

    public MainViewModel(Application application) {
        super(application);
        loginRepository = new LoginRepository(AppDatabase.getInstance(application).sessionDao());
    }

    public void logOut() {
        new Thread(() -> {
            loginRepository.logOut();
            isLogged = false;
            isLoggedMutableLiveData.postValue(false);
        }).start();
    }

    public LiveData<Boolean> getIsLoggedMutableLiveData() {
        return isLoggedMutableLiveData;
    }

    public boolean isLogged() {
        return isLogged;
    }
}