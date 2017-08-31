package com.educareapps.quiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.educareapps.quiz.R;

public class ResultActivity extends BaseActivity implements View.OnClickListener {

    ResultActivity activity;
    TextView tvTotalPlayed, tvCorrectAnswer, tvWrongAnswer, tvTotalScore, tvTotalDuration;
    Button btnPlayAgain;
    ImageButton ibtnCrossResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        activity = this;

        tvTotalPlayed = (TextView) findViewById(R.id.tvTotalPlayed);
        tvCorrectAnswer = (TextView) findViewById(R.id.tvCorrectAnswer);
        tvWrongAnswer = (TextView) findViewById(R.id.tvWrongAnswer);
        tvTotalScore = (TextView) findViewById(R.id.tvTotalScore);
        tvTotalDuration = (TextView) findViewById(R.id.tvTotalDuration);
        btnPlayAgain = (Button) findViewById(R.id.btnPlayAgain);
        ibtnCrossResult = (ImageButton) findViewById(R.id.ibtnCrossResult);

        btnPlayAgain.setOnClickListener(this);
        ibtnCrossResult.setOnClickListener(this);
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
