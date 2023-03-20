package ru.sejapoe.digitalhotel.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ru.sejapoe.digitalhotel.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }
}