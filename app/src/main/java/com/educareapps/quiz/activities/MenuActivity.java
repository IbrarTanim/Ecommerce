package com.educareapps.quiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.educareapps.quiz.R;

public class MenuActivity extends BaseActivity {

    MenuActivity activity;
    ImageButton ibtnMenue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        activity = this;
        ibtnMenue = (ImageButton) findViewById(R.id.ibtnMenue);


        ibtnMenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, QuizActivity.class));

            }
        });
    }
}
