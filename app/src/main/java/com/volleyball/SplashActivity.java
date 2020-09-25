package com.volleyball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.volleyball.activities.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private static int time_out=1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i= new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
            }
        },time_out);
    }
}