package ru.sejapoe.digitalhotel.ui.loading;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import ru.sejapoe.digitalhotel.data.db.AppDatabase;
import ru.sejapoe.digitalhotel.data.db.SessionDao;
import ru.sejapoe.digitalhotel.data.model.Session;
import ru.sejapoe.digitalhotel.data.network.RetrofitProvider;

public class LoadingViewModel extends AndroidViewModel {
    private final SessionDao sessionDao;
    private final MutableLiveData<Boolean> isLogged = new MutableLiveData<>(null);

    public LoadingViewModel(@NonNull Application application) {
        super(application);
        sessionDao = AppDatabase.getInstance(application).sessionDao();
    }

    public void load() {
        new Thread(() -> {
            Session session = sessionDao.get();
            RetrofitProvider.createInstance(session);
            isLogged.postValue(session != null);
        }).start();
    }

    public LiveData<Boolean> isLogged() {
        return isLogged;
    }
}
