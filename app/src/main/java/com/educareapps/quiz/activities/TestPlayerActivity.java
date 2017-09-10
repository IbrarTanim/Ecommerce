package com.educareapps.quiz.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.educareapps.quiz.R;
import com.educareapps.quiz.dao.CSVQuestionTable;
import com.educareapps.quiz.dao.TestTable;
import com.educareapps.quiz.manager.DatabaseManager;
import com.educareapps.quiz.utilities.StaticAccess;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TestPlayerActivity extends Activity implements View.OnClickListener {

    long test_id = -1;
    TextView tvQuestion;
    RadioButton rbtnOptionOne, rbtnOptionTwo, rbtnOptionThree, rbtnOptionFour;
    ArrayList<CSVQuestionTable> allQuestions, questionsForPlay;
    DatabaseManager databaseManager;
    TestPlayerActivity activity;
    ImageButton ibtnPreviousQuestion, ibtnNextQuestion;
    CSVQuestionTable question;
    TestTable aTest;
    /// for generating a question list as per test rules
    int start = 0;
    int end = 0;

    /// for moving next question
    int startingQuestionIndex = 0;
    int endingQuestionIndex = 0;

    //// for getting correct answer & wrong answer question list
    ArrayList<CSVQuestionTable> correctQuestionList;
    ArrayList<CSVQuestionTable> wrongQuestionList;
    TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_player);
        activity = this;
        databaseManager = new DatabaseManager(activity);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
      /*  ibtnPreviousQuestion = (ImageButton) findViewById(R.id.ibtnPreviousQuestion);
        ibtnNextQuestion = (ImageButton) findViewById(R.id.ibtnNextQuestion);*/
        rbtnOptionOne = (RadioButton) findViewById(R.id.rbtnOptionOne);
        rbtnOptionTwo = (RadioButton) findViewById(R.id.rbtnOptionTwo);
        rbtnOptionThree = (RadioButton) findViewById(R.id.rbtnOptionThree);
        rbtnOptionFour = (RadioButton) findViewById(R.id.rbtnOptionFour);

        rbtnOptionOne.setOnClickListener(this);
        rbtnOptionTwo.setOnClickListener(this);
        rbtnOptionThree.setOnClickListener(this);
        rbtnOptionFour.setOnClickListener(this);
        test_id = getIntent().getLongExtra(StaticAccess.TEST_ID, -1);
        if (test_id != -1) {
            questionsForPlay = new ArrayList<>();
            correctQuestionList = new ArrayList<>();
            wrongQuestionList = new ArrayList<>();
            aTest = databaseManager.getTestTableById(test_id);
            allQuestions = new ArrayList<CSVQuestionTable>(databaseManager.getQuestionSetTableById(aTest.getQuestion_set_id()).getCsvQuestionToQuestionSet());
            start = Integer.parseInt(aTest.getQuestion_start_from());
            end = Integer.parseInt(aTest.getQuestion_start_to());
            for (int i = start; i <= end - 1; i++) {
                questionsForPlay.add(allQuestions.get(i));
            }
            endingQuestionIndex = questionsForPlay.size();
            question = questionsForPlay.get(startingQuestionIndex);
            quizStartTime = System.currentTimeMillis();
            initViewWithQuestion();
        }
    }

    private void initViewWithQuestion() {
        resetOptions();
        tvStatus.setText("Total played: " + String.valueOf(startingQuestionIndex + 1) + "/" + String.valueOf(endingQuestionIndex));
        tvQuestion.setText(question.getQuestion());
        rbtnOptionOne.setText(question.getOption_one());
        rbtnOptionTwo.setText(question.getOption_two());
        rbtnOptionThree.setText(question.getOption_three());
        rbtnOptionFour.setText(question.getOption_four());
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
                //startActivity(new Intent(activity, ResultActivity.class));
                checkCorrectAnswer(rbtnOptionOne.getText().toString());
                break;
            case R.id.rbtnOptionTwo:
                rbtnOptionOne.setChecked(false);
                rbtnOptionThree.setChecked(false);
                rbtnOptionFour.setChecked(false);
                checkCorrectAnswer(rbtnOptionTwo.getText().toString());

                break;
            case R.id.rbtnOptionThree:
                rbtnOptionOne.setChecked(false);
                rbtnOptionTwo.setChecked(false);
                rbtnOptionFour.setChecked(false);
                checkCorrectAnswer(rbtnOptionThree.getText().toString());

                break;
            case R.id.rbtnOptionFour:
                rbtnOptionOne.setChecked(false);
                rbtnOptionTwo.setChecked(false);
                rbtnOptionThree.setChecked(false);
                checkCorrectAnswer(rbtnOptionFour.getText().toString());

                break;
            /*case R.id.ibtnPreviousQuestion:
                break;
            case R.id.ibtnNextQuestion:
                break;*/


        }
    }


    private void checkCorrectAnswer(String userSayingAnswer) {
        if (userSayingAnswer.equals(question.getAnswer())) {
            /// add correct question in the list first
            correctQuestionList.add(question);
            /// then check is test over or not then  move to next question
            checkTestOver();
        } else {
            /// add wrong questin in the list first
            wrongQuestionList.add(question);
            /// then check is test over or not then  move to next question
            checkTestOver();
        }
    }

    long quizStartTime = -1;
    long quizEndTime = -1;
    String duration = "";

    void checkTestOver() {
        if (startingQuestionIndex == endingQuestionIndex - 1) {

            quizEndTime = System.currentTimeMillis();

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date date1 = new Date(quizStartTime);
            Date date2 = new Date(quizEndTime);
            sdf.format(date1);
            sdf.format(date2);
            long difference = date2.getTime() - date1.getTime();
            long diffMinutes = difference / (60 * 1000) % 60;
            duration = String.valueOf(diffMinutes);

            /// go for result activity
            Intent resIntent = new Intent(activity, ResultActivity.class);
            resIntent.putExtra(StaticAccess.TAG_TOTAL_PLAYED, startingQuestionIndex + 1);
            resIntent.putExtra(StaticAccess.TAG_CORRECT_ANSWER, correctQuestionList.size());
            resIntent.putExtra(StaticAccess.TAG_WRONG_ANSWER, wrongQuestionList.size());
            resIntent.putExtra(StaticAccess.TAG_TOTAL_SCORE, correctQuestionList.size());
            resIntent.putExtra(StaticAccess.TAG_TOTAL_DURATION, duration);
            resIntent.putExtra(StaticAccess.QUESTION_SET_ID, aTest.getQuestion_set_id());
            resIntent.putExtra(StaticAccess.TAG_COME_FROM, 1);
            startActivity(resIntent);
            finish();
        } else {
            startingQuestionIndex++;
            question = questionsForPlay.get(startingQuestionIndex);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    initViewWithQuestion();
                }
            }, 1500);
        }
    }

}
