package com.educareapps.quiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.educareapps.quiz.R;

public class MenuActivity extends BaseActivity {

    MenuActivity activity;
    Button btnGeneral;
    Button btnBlind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        activity = this;
        btnGeneral = (Button) findViewById(R.id.btnGeneral);
        btnBlind = (Button) findViewById(R.id.btnBlind);


        btnGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, MainActivity.class));
                finish();

            }
        });

        btnBlind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, QuizActivity.class));
                finish();

            }
        });

    }
}
