package com.nss.nss;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = new Handler();
        handler.postDelayed(() -> {
            Intent i = new Intent(Splash.this, RedesMovilesActivity.class);
            startActivity(i);
            finish();
        }, 3000);
    }
}
