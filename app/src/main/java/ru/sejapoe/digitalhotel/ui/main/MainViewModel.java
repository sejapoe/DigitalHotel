package ru.sejapoe.digitalhotel.ui.main;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.sejapoe.digitalhotel.data.db.AppDatabase;
import ru.sejapoe.digitalhotel.data.network.HttpProvider;
import ru.sejapoe.digitalhotel.data.repository.LoginRepository;

public class MainViewModel extends AndroidViewModel {
    private final LoginRepository loginRepository;
    private final MutableLiveData<Boolean> isLogged = new MutableLiveData<>(true);

    public MainViewModel(Application application) {
        super(application);
        loginRepository = new LoginRepository(AppDatabase.getInstance(application).sessionDao());
    }

    public void logOut() {
        loginRepository.logOut();
        isLogged.postValue(false);
    }

    public LiveData<Boolean> isLogged() {
        return isLogged;
    }
}