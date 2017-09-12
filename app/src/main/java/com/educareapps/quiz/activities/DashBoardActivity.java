package com.educareapps.quiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.educareapps.quiz.R;
import com.educareapps.quiz.adapter.ScoreBoardAdapter;
import com.educareapps.quiz.dao.LeaderBoardTable;
import com.educareapps.quiz.dao.UserTable;
import com.educareapps.quiz.manager.DatabaseManager;
import com.educareapps.quiz.parser.LeaderBoardUpdater;
import com.educareapps.quiz.utilities.InternetAvailabilityCheck;
import com.educareapps.quiz.utilities.SharedPreferenceValue;
import com.educareapps.quiz.utilities.StaticAccess;

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
    UserTable user;

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
        user_id = SharedPreferenceValue.getUserID(activity);
        if (user_id != -1) {
            user = databaseManager.getUserTableById(user_id);
            tvEmailDashboard.setText(user.getEmail());
            if (!SharedPreferenceValue.getLeaderBoardOK(activity)) {
                if (InternetAvailabilityCheck.getConnectivityStatus(activity) != StaticAccess.TYPE_NOT_CONNECTED) {
                    checkServerLeaderBoardForUser();
                } else {
                    Toast.makeText(activity, "Connect with internet then try again", Toast.LENGTH_SHORT).show();
                }
            }
            scoreList = user.getLeaderBoardToUser();
            if (scoreList != null && scoreList.size() > 0) {
                scoreBoardAdapter = new ScoreBoardAdapter(scoreList, activity);
                lvScoreBoard.setAdapter(scoreBoardAdapter);
            }
        }
        ibtnBackDashBoard.setOnClickListener(this);
        btnStart.setOnClickListener(this);
    }

    private void checkServerLeaderBoardForUser() {
        new LeaderBoardUpdater(activity).settingLeaderBoardAsUser(databaseManager.getUserTableById(user_id).getUser_id());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtnBackDashBoard:
                System.exit(0);
                break;
            case R.id.btnStart:
                startActivity(new Intent(activity, CourseActivity.class));
                finish();
                break;
        }
    }
}
