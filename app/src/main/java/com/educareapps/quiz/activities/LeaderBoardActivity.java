package com.educareapps.quiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.educareapps.quiz.R;

public class LeaderBoardActivity extends BaseActivity implements View.OnClickListener {
    LeaderBoardActivity activity;
    ImageButton ibtnBackBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        activity = this;

        ibtnBackBoard = (ImageButton) findViewById(R.id.ibtnBackBoard);
        ibtnBackBoard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtnBackBoard:
                startActivity(new Intent(activity, MainActivity.class));
                finish();
                break;
        }
    }
}
