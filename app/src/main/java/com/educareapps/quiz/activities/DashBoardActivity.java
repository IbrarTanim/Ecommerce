package com.educareapps.quiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.educareapps.quiz.R;
import com.educareapps.quiz.adapter.ScoreBoardAdapter;
import com.educareapps.quiz.dao.LeaderBoardTable;
import com.educareapps.quiz.manager.DatabaseManager;

import java.util.List;

public class DashBoardActivity extends BaseActivity implements View.OnClickListener {

    DashBoardActivity activity;
    TextView tvEmailDashboard;
    ImageButton ibtnBackDashBoard;
    ImageView ivProfilePic;
    Button btnStart;
    ListView lvScoreBoard;
    ScoreBoardAdapter scoreBoardAdapter;
    long user_id = -1;
    DatabaseManager databaseManager;
    List<LeaderBoardTable> scoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        activity = this;
        databaseManager = new DatabaseManager(activity);
        tvEmailDashboard = (TextView) findViewById(R.id.tvEmailDashboard);
        btnStart = (Button) findViewById(R.id.btnStart);
        lvScoreBoard = (ListView) findViewById(R.id.lvScoreBoard);
        ibtnBackDashBoard = (ImageButton) findViewById(R.id.ibtnBackDashBoard);
        ivProfilePic = (ImageView) findViewById(R.id.ivProfilePic);
        if (user_id != -1) {
            scoreList = databaseManager.getUserTableById(user_id).getLeaderBoardToUser();
            scoreBoardAdapter = new ScoreBoardAdapter(scoreList, activity);
            lvScoreBoard.setAdapter(scoreBoardAdapter);
        }
        ibtnBackDashBoard.setOnClickListener(this);
        btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtnBackDashBoard:
                startActivity(new Intent(activity, MainActivity.class));
                finish();
                break;
            case R.id.btnStart:
                startActivity(new Intent(activity, CourseActivity.class));
                finish();
                break;
        }
    }
}
