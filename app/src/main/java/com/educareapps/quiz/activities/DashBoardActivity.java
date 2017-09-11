package com.educareapps.quiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.educareapps.quiz.R;

public class DashBoardActivity extends BaseActivity implements View.OnClickListener {

    DashBoardActivity activity;
    TextView tvFullNAme, tvFirstName, tvLastName, tvEmail, tvAddress, tvOccupation, tvPhone;
    ImageButton ibtnBackDashBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        activity = this;

        tvFullNAme = (TextView) findViewById(R.id.tvFullNAme);
        tvFirstName = (TextView) findViewById(R.id.tvFirstName);
        tvLastName = (TextView) findViewById(R.id.tvLastName);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvOccupation = (TextView) findViewById(R.id.tvOccupation);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        ibtnBackDashBoard = (ImageButton) findViewById(R.id.ibtnBackDashBoard);
        ibtnBackDashBoard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtnBackDashBoard:
                startActivity(new Intent(activity, LoginActivity.class));
                finish();
                break;
        }
    }
}
