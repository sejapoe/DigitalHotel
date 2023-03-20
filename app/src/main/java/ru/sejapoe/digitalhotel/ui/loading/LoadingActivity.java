package ru.sejapoe.digitalhotel.ui.loading;

import androidx.appcompat.app.AppCompatActivity;

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

        new Thread(() -> {
            Log.d("DigitalHotelApplication", "Waiting got database");
            SessionDao sessionDao = AppDatabase.getInstance(this).sessionDao();
            Session session = sessionDao.get();
            Log.d("DigitalHotelApplication", String.valueOf(session));
            HttpProvider.createInstance(session);
            Class<? extends Activity> cls = session == null ? LoginActivity.class : MainActivity.class;
            Intent intent = new Intent(this, cls);
            startActivity(intent);
            finish();
        }).start();
    }
}