package com.educareapps.quiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.educareapps.quiz.R;
import com.educareapps.quiz.dao.CSVQuestionTable;
import com.educareapps.quiz.dao.QuestionSetTable;
import com.educareapps.quiz.manager.DatabaseManager;
import com.educareapps.quiz.utilities.SpeechToTextUtil;
import com.educareapps.quiz.utilities.StaticAccess;
import com.educareapps.quiz.utilities.TextToSpeechManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class QuizActivity extends BaseActivity implements View.OnClickListener, TextToSpeechManager.FinishSpeakListener, SpeechToTextUtil.SpeechListeningFinishListener {
    QuizActivity activity;
    EditText edtTTS;
    Button btnSpeek;
    TextView tvQuestion;
    RadioButton rbtnOptionOne, rbtnOptionTwo, rbtnOptionThree, rbtnOptionFour;
    TextToSpeechManager textToSpeechManager;
    SpeechToTextUtil speechToTextUtil;
    DatabaseManager databaseManager;
    List<QuestionSetTable> questionSetList;
    List<CSVQuestionTable> csvQuestionList;
    CSVQuestionTable question;
    int start = 0;
    int end = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        activity = this;
        databaseManager = new DatabaseManager(activity);
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
        csvQuestionList = databaseManager.listCSVQuestionTable();
        end = csvQuestionList.size();
        question = csvQuestionList.get(start);
        initViewWithQuestion();
        startQuiz();
    }

    private void initViewWithQuestion() {
        resetOptions();
        tvQuestion.setText(question.getQuestion());
        rbtnOptionOne.setText(question.getOption_one());
        rbtnOptionTwo.setText(question.getOption_two());
        rbtnOptionThree.setText(question.getOption_three());
        rbtnOptionFour.setText(question.getOption_four());
    }

    int canPlay = 10;
    long quizStartTime = -1;
    long quizEndTime = -1;
    int totalPlayed = -1;
    int correctAnswer = 0;
    int wrongAnswer = 0;
    String duration = "";
    int totalScore = 0;

    public void startQuiz() {
        quizStartTime = System.currentTimeMillis();
        textToSpeechManager.speak(question.getQuestion() + " " +
                "The Options are: " +
                "Option 1       " + rbtnOptionOne.getText().toString() +
                "Option 2       " + rbtnOptionTwo.getText().toString() +
                "Option 3       " + rbtnOptionThree.getText().toString() +
                "Option 4       " + rbtnOptionFour.getText().toString()

        );
        isForAnswer = true;
    }


    void resetOptions() {
        rbtnOptionOne.setChecked(false);
        rbtnOptionTwo.setChecked(false);
        rbtnOptionThree.setChecked(false);
        rbtnOptionFour.setChecked(false);
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
        if (result.equals(question.getAnswer())) {
            checkRadioAnswer(question.getAnswer());
            textToSpeechManager.speak("Correct Answer");
            isForAnswer = false;
            correctAnswer++;
            totalScore++;
            if (checkQuizOver()) {
                playQuizOver();

            } else {
                movingToNextQuestion();
            }
        } else {
            textToSpeechManager.speak("Wrong Answer");
            wrongAnswer++;
            isForAnswer = false;
            if (checkQuizOver()) {
                playQuizOver();
            } else {
                movingToNextQuestion();
            }
        }
    }

    private void playQuizOver() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textToSpeechManager.speak("Quiz Over");
                isForAnswer = false;
            }
        }, 1200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goToResultActivity();
            }
        }, 3000);
    }

    private void goToResultActivity() {
        Intent resIntent = new Intent(activity, ResultActivity.class);
        resIntent.putExtra(StaticAccess.TAG_TOTAL_PLAYED, totalPlayed);
        resIntent.putExtra(StaticAccess.TAG_CORRECT_ANSWER, correctAnswer);
        resIntent.putExtra(StaticAccess.TAG_WRONG_ANSWER, wrongAnswer);
        resIntent.putExtra(StaticAccess.TAG_TOTAL_SCORE, totalScore);
        resIntent.putExtra(StaticAccess.TAG_TOTAL_DURATION, duration);
        startActivity(resIntent);
        finish();
    }

    private void checkRadioAnswer(String answer) {

        if (rbtnOptionOne.getText().toString().equals(answer)) {
            rbtnOptionOne.setChecked(true);
        } else if (rbtnOptionTwo.getText().toString().equals(answer)) {
            rbtnOptionTwo.setChecked(true);

        } else if (rbtnOptionThree.getText().toString().equals(answer)) {
            rbtnOptionThree.setChecked(true);

        } else if (rbtnOptionFour.getText().toString().equals(answer)) {
            rbtnOptionFour.setChecked(true);

        }
    }

    //// moving to next questions
    public void movingToNextQuestion() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textToSpeechManager.speak("Moving For Next Question");
            }
        }, 1200);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                question = csvQuestionList.get(start);
                initViewWithQuestion();
                startQuiz();
            }
        }, 4000);
    }


    private boolean checkQuizOver() {
        boolean isOver = false;
        if (end > canPlay) {
            end = canPlay;
        }
        if (start != end - 1) {
            start++;

        } else if (start == end-1) {
            //// game over
            totalPlayed = start;

            quizEndTime = System.currentTimeMillis();
            long totalDuration = quizEndTime - quizStartTime;
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date resultdate = new Date(totalDuration);
            System.out.println(sdf.format(resultdate));
            duration = sdf.format(resultdate);
            isOver = true;

        }
        return isOver;
    }

    @Override
    public void getErrorMsg(String result) {
        textToSpeechManager.speak(result);
        /// start listening again for answer jus make isForAnswer true. it will automatecally detect answer
        isForAnswer = true;
    }

    @Override
    protected void onPause() {
        textToSpeechManager.stopTextToSpeech();
        speechToTextUtil.destroySpeechToText();
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (textToSpeechManager == null) {
            textToSpeechManager = new TextToSpeechManager(activity, this);
        }
        if (speechToTextUtil == null) {
            speechToTextUtil = new SpeechToTextUtil(activity, this);
        }
        startQuiz();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        textToSpeechManager.stopTextToSpeech();
        speechToTextUtil.destroySpeechToText();
        super.onDestroy();
    }
}
