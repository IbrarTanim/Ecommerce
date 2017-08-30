package com.educareapps.quiz.activities;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.educareapps.quiz.R;
import com.educareapps.quiz.utilities.SpeechToTextUtil;
import com.educareapps.quiz.utilities.TextToSpeechManager;

public class QuizActivity extends BaseActivity implements View.OnClickListener, TextToSpeechManager.FinishSpeakListener, SpeechToTextUtil.SpeechListeningFinishListener {
    QuizActivity activity;
    EditText edtTTS;
    Button btnSpeek;
    TextView tvQuestion;
    RadioButton rbtnOptionOne, rbtnOptionTwo, rbtnOptionThree, rbtnOptionFour;
    TextToSpeechManager textToSpeechManager;
    SpeechToTextUtil speechToTextUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        activity = this;
        textToSpeechManager = new TextToSpeechManager(activity, this);
        speechToTextUtil = new SpeechToTextUtil(activity, this);
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        rbtnOptionOne = (RadioButton) findViewById(R.id.rbtnOptionOne);
        rbtnOptionTwo = (RadioButton) findViewById(R.id.rbtnOptionTwo);
        rbtnOptionThree = (RadioButton) findViewById(R.id.rbtnOptionThree);
        rbtnOptionFour = (RadioButton) findViewById(R.id.rbtnOptionFour);

        rbtnOptionOne.setOnClickListener(this);
        rbtnOptionTwo.setOnClickListener(this);
        rbtnOptionThree.setOnClickListener(this);
        rbtnOptionFour.setOnClickListener(this);
        startQuize();

    }

    public void startQuize() {
        String data = tvQuestion.getText().toString();
        textToSpeechManager.speak(data + " " +
                "The Options are: " +
                "Option 1       " + rbtnOptionOne.getText().toString() +
                "Option 2       " + rbtnOptionTwo.getText().toString() +
                "Option 3       " + rbtnOptionThree.getText().toString() +
                "Option 4       " + rbtnOptionFour.getText().toString()

        );
        isForAnswer = true;
    }

    @Override
    protected void onDestroy() {
        textToSpeechManager.stopTextToSpeech();
        speechToTextUtil.destroySpeechToText();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rbtnOptionOne:
                rbtnOptionTwo.setChecked(false);
                rbtnOptionThree.setChecked(false);
                rbtnOptionFour.setChecked(false);

                break;
            case R.id.rbtnOptionTwo:
                rbtnOptionOne.setChecked(false);
                rbtnOptionThree.setChecked(false);
                rbtnOptionFour.setChecked(false);

                break;
            case R.id.rbtnOptionThree:
                rbtnOptionOne.setChecked(false);
                rbtnOptionTwo.setChecked(false);
                rbtnOptionFour.setChecked(false);

                break;
            case R.id.rbtnOptionFour:
                rbtnOptionOne.setChecked(false);
                rbtnOptionTwo.setChecked(false);
                rbtnOptionThree.setChecked(false);
                break;


        }
    }

    boolean isForAnswer = false;

    @Override
    public void speakFinished() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isForAnswer) {/// if true start listening for geting answer from user
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            speechToTextUtil.startListening();
                        }
                    }, 1200);
                }
            }
        });
    }

    @Override
    public void getListeningResult(String result) {
        Log.e("detect", result);
        if (result.equals(rbtnOptionOne.getText().toString())) {
            rbtnOptionOne.setSelected(true);
            textToSpeechManager.speak("Correct Answer. Moving For Next Question");
            isForAnswer = false;
            movingToNextQuestion();
        } else {
            textToSpeechManager.speak("Wrong Answer. Moving For Next Question");
            isForAnswer = false;
            movingToNextQuestion();
        }
    }

    //// moving to next questions
    public void movingToNextQuestion() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startQuize();
            }
        }, 4000);
    }

    @Override
    public void getErrorMsg(String result) {
        textToSpeechManager.speak(result);
        /// start listening again for answer jus make isForAnswer true. it will automatecally detect answer
        isForAnswer = true;
    }
}
