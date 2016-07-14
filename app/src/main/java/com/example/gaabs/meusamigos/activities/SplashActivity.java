package com.example.gaabs.meusamigos.activities;

import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.gaabs.meusamigos.R;

public class SplashActivity extends AppCompatActivity {

    final static int DELAY_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AsyncTaskLoader taskLoader = new AsyncTaskLoader(this) {
            @Override
            public Object loadInBackground() {
                try {
                    wait(DELAY_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return null;
                }
            }
        };
        taskLoader.loadInBackground();

    }

}
