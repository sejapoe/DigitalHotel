package ru.sejapoe.digitalhotel.ui.loading;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import ru.sejapoe.digitalhotel.R;
import ru.sejapoe.digitalhotel.data.db.AppDatabase;
import ru.sejapoe.digitalhotel.data.network.HttpProvider;
import ru.sejapoe.digitalhotel.data.model.Session;
import ru.sejapoe.digitalhotel.data.db.SessionDao;
import ru.sejapoe.digitalhotel.ui.login.LoginActivity;
import ru.sejapoe.digitalhotel.ui.main.MainActivity;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        LoadingViewModel loadingViewModel = new ViewModelProvider(this).get(LoadingViewModel.class);
        loadingViewModel.load();

        loadingViewModel.getLoadedActivityMutableLiveData().observe(this, cls -> {
            if (cls == null) return;
            Intent intent = new Intent(this, cls);
            startActivity(intent);
            finish();
        });
    }
}