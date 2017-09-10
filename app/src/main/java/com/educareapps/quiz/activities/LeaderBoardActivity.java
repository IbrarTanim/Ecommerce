package com.educareapps.quiz.activities;

import android.os.Bundle;
import android.widget.ImageButton;

import com.educareapps.quiz.R;

public class LeaderBoardActivity extends BaseActivity {
    LeaderBoardActivity activity;
    ImageButton ibtnBackLeaderBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        activity = this;
    }
}
