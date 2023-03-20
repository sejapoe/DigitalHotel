package ru.sejapoe.digitalhotel.ui.loading;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import ru.sejapoe.digitalhotel.data.db.AppDatabase;
import ru.sejapoe.digitalhotel.data.db.SessionDao;
import ru.sejapoe.digitalhotel.data.model.Session;
import ru.sejapoe.digitalhotel.data.network.HttpProvider;
import ru.sejapoe.digitalhotel.ui.login.LoginActivity;
import ru.sejapoe.digitalhotel.ui.main.MainActivity;

public class LoadingViewModel extends AndroidViewModel {
    private final SessionDao sessionDao;
    private final MutableLiveData<Class<? extends Activity>> loadedActivityMutableLiveData = new MutableLiveData<>(null);

    public LoadingViewModel(@NonNull Application application) {
        super(application);
        sessionDao = AppDatabase.getInstance(application).sessionDao();
    }

    public void load() {
        new Thread(() -> {
            Log.d("DigitalHotelApplication", "Waiting got database");
            Session session = sessionDao.get();
            Log.d("DigitalHotelApplication", String.valueOf(session));
            HttpProvider.createInstance(session);
            Class<? extends Activity> cls = session == null ? LoginActivity.class : MainActivity.class;
            loadedActivityMutableLiveData.postValue(cls);
        }).start();
    }

    public LiveData<Class<? extends Activity>> getLoadedActivityMutableLiveData() {
        return loadedActivityMutableLiveData;
    }
}
