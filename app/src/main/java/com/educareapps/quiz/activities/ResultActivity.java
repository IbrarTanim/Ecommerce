package com.educareapps.quiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.educareapps.quiz.R;
import com.educareapps.quiz.utilities.StaticAccess;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        activity = this;

        totalPlayed = getIntent().getIntExtra(StaticAccess.TAG_TOTAL_PLAYED, -1);
        correctAnswer = getIntent().getIntExtra(StaticAccess.TAG_CORRECT_ANSWER, 0);
        wrongAnswer = getIntent().getIntExtra(StaticAccess.TAG_WRONG_ANSWER, 0);
        totalScore = getIntent().getIntExtra(StaticAccess.TAG_TOTAL_SCORE, 0);
        duration = getIntent().getStringExtra(StaticAccess.TAG_TOTAL_DURATION);


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
        tvTotalPlayed.setText(String.valueOf(totalPlayed+1));
        tvCorrectAnswer.setText(String.valueOf(correctAnswer));
        tvWrongAnswer.setText(String.valueOf(wrongAnswer));
        tvTotalScore.setText(String.valueOf(totalScore));
        tvTotalDuration.setText(String.valueOf(duration));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPlayAgain:
                startActivity(new Intent(activity, QuizActivity.class));
                break;

            case R.id.ibtnCrossResult:
                startActivity(new Intent(activity, MenuActivity.class));

                break;
        }

    }
}
