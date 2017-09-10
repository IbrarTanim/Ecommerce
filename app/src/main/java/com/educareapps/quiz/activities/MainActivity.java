package com.educareapps.quiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.educareapps.quiz.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    MainActivity activity;

    Button btnDashBoard, btnCourses, btnLeaderBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        btnDashBoard = (Button) findViewById(R.id.btnDashBoard);
        btnCourses = (Button) findViewById(R.id.btnCourses);
        btnLeaderBoard = (Button) findViewById(R.id.btnLeaderBoard);

        btnDashBoard.setOnClickListener(this);
        btnCourses.setOnClickListener(this);
        btnLeaderBoard.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDashBoard:
                startActivity(new Intent(activity, DashBoardActivity.class));
                finish();
                break;

            case R.id.btnCourses:
                startActivity(new Intent(activity, CourseActivity.class));
                finish();
                break;

            case R.id.btnLeaderBoard:
                startActivity(new Intent(activity, LeaderBoardActivity.class));
                finish();
                break;


        }
    }
}
