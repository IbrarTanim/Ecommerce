package com.educareapps.quiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.educareapps.quiz.R;
import com.educareapps.quiz.dao.LeaderBoardTable;
import com.educareapps.quiz.manager.DatabaseManager;
import com.educareapps.quiz.utilities.StaticAccess;

import java.util.Date;

public class ResultActivity extends BaseActivity implements View.OnClickListener {

    ResultActivity activity;
    TextView tvTotalPlayed, tvCorrectAnswer, tvWrongAnswer, tvTotalScore, tvTotalDuration;
    Button btnPlayAgain;
    ImageButton ibtnCrossResult;

    int totalPlayed = -1;
    int correctAnswer = 0;
    int wrongAnswer = 0;
    String duration = "";
    int totalScore = 0;
    int comeFrom = -1;
    long question_set_id = -1;
    long user_id = -1;
    long test_id = -1;
    DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        activity = this;
        databaseManager = new DatabaseManager(activity);
        totalPlayed = getIntent().getIntExtra(StaticAccess.TAG_TOTAL_PLAYED, -1);
        correctAnswer = getIntent().getIntExtra(StaticAccess.TAG_CORRECT_ANSWER, 0);
        wrongAnswer = getIntent().getIntExtra(StaticAccess.TAG_WRONG_ANSWER, 0);
        totalScore = getIntent().getIntExtra(StaticAccess.TAG_TOTAL_SCORE, 0);
        duration = getIntent().getStringExtra(StaticAccess.TAG_TOTAL_DURATION);
        comeFrom = getIntent().getIntExtra(StaticAccess.TAG_COME_FROM, -1);
        question_set_id = getIntent().getLongExtra(StaticAccess.QUESTION_SET_ID, -1);
        user_id = getIntent().getLongExtra(StaticAccess.TAG_USER_ID, -1);
        test_id = getIntent().getLongExtra(StaticAccess.TEST_ID, -1);


        tvTotalPlayed = (TextView) findViewById(R.id.tvTotalPlayed);
        tvCorrectAnswer = (TextView) findViewById(R.id.tvCorrectAnswer);
        tvWrongAnswer = (TextView) findViewById(R.id.tvWrongAnswer);
        tvTotalScore = (TextView) findViewById(R.id.tvTotalScore);
        tvTotalDuration = (TextView) findViewById(R.id.tvTotalDuration);
        btnPlayAgain = (Button) findViewById(R.id.btnPlayAgain);
        ibtnCrossResult = (ImageButton) findViewById(R.id.ibtnCrossResult);

        btnPlayAgain.setOnClickListener(this);
        ibtnCrossResult.setOnClickListener(this);
        initResult();
    }

    private void initResult() {
        tvTotalPlayed.setText(String.valueOf(totalPlayed));
        tvCorrectAnswer.setText(String.valueOf(correctAnswer));
        tvWrongAnswer.setText(String.valueOf(wrongAnswer));
        tvTotalScore.setText(String.valueOf(totalScore));
        tvTotalDuration.setText(String.valueOf(duration) + " min");
        LeaderBoardTable leaderBoardTable = new LeaderBoardTable();
        leaderBoardTable.setScore(correctAnswer);
        leaderBoardTable.setNegative(String.valueOf(wrongAnswer));
        leaderBoardTable.setTotal_duration(String.valueOf(duration));
        leaderBoardTable.setUser_id(user_id);
        leaderBoardTable.setTest_id(test_id);
        leaderBoardTable.setCreated_at(new Date());
        databaseManager.insertLeaderBoardTable(leaderBoardTable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPlayAgain:
                if (comeFrom == 0) {
                    startActivity(new Intent(activity, QuizActivity.class));
                } else {
                    if (question_set_id != -1) {
                        Intent testIntent = new Intent(activity, TestListActivity.class);
                        testIntent.putExtra(StaticAccess.QUESTION_SET_ID, question_set_id);
                        startActivity(testIntent);
                        finish();
                    }
                }
                break;

            case R.id.ibtnCrossResult:
                if (comeFrom == 0) {
                    startActivity(new Intent(activity, MenuActivity.class));
                } else {
                    if (question_set_id != -1) {
                        Intent testIntent = new Intent(activity, TestListActivity.class);
                        testIntent.putExtra(StaticAccess.QUESTION_SET_ID, question_set_id);
                        startActivity(testIntent);
                        finish();
                    }
                }
                break;
        }

    }
}
