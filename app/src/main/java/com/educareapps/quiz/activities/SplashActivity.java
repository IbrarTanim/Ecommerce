package com.educareapps.quiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.educareapps.quiz.R;

public class SplashActivity extends BaseActivity {

    SplashActivity activity;
    private int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        activity = this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(activity, MenuActivity.class));
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
