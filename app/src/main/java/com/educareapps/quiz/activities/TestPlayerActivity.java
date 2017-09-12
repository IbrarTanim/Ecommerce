package com.educareapps.quiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.educareapps.quiz.R;
import com.educareapps.quiz.dao.CSVQuestionTable;
import com.educareapps.quiz.dao.TestTable;
import com.educareapps.quiz.manager.DatabaseManager;
import com.educareapps.quiz.utilities.SharedPreferenceValue;
import com.educareapps.quiz.utilities.StaticAccess;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class TestPlayerActivity extends BaseActivity implements View.OnClickListener {

    long test_id = -1;
    long user_id = -1;
    TextView tvQuestion;
    ArrayList<CSVQuestionTable> allQuestions, questionsForPlay;
    DatabaseManager databaseManager;
    TestPlayerActivity activity;
    ImageButton ibtnTestPlayerBack;
    Button btnNextQuestion;
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
    TextView tvStatus, tvTimer;

    LinearLayout llOptionOne, llOptionTwo, llOptionThree, llOptionFour;
    TextView tvOptionOne, tvOptionTwo, tvOptionThree, tvOptionFour;
    ImageButton ibtnOptionOneTick, ibtnOptionTwo, ibtnOptionThree, ibtnOptionFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_player);
        activity = this;
        databaseManager = new DatabaseManager(activity);
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        btnNextQuestion = (Button) findViewById(R.id.btnNextQuestion);

        llOptionOne = (LinearLayout) findViewById(R.id.llOptionOne);
        llOptionTwo = (LinearLayout) findViewById(R.id.llOptionTwo);
        llOptionThree = (LinearLayout) findViewById(R.id.llOptionThree);
        llOptionFour = (LinearLayout) findViewById(R.id.llOptionFour);

        tvOptionOne = (TextView) findViewById(R.id.tvOptionOne);
        tvOptionTwo = (TextView) findViewById(R.id.tvOptionTwo);
        tvOptionThree = (TextView) findViewById(R.id.tvOptionThree);
        tvOptionFour = (TextView) findViewById(R.id.tvOptionFour);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvTimer = (TextView) findViewById(R.id.tvTimer);

        ibtnOptionOneTick = (ImageButton) findViewById(R.id.ibtnOptionOneTick);
        ibtnOptionTwo = (ImageButton) findViewById(R.id.ibtnOptionTwo);
        ibtnOptionThree = (ImageButton) findViewById(R.id.ibtnOptionThree);
        ibtnOptionFour = (ImageButton) findViewById(R.id.ibtnOptionFour);

        ibtnTestPlayerBack = (ImageButton) findViewById(R.id.ibtnTestPlayerBack);


        llOptionOne.setOnClickListener(this);
        llOptionTwo.setOnClickListener(this);
        llOptionThree.setOnClickListener(this);
        llOptionFour.setOnClickListener(this);

        ibtnTestPlayerBack.setOnClickListener(this);

        btnNextQuestion.setOnClickListener(this);
        test_id = getIntent().getLongExtra(StaticAccess.TEST_ID, -1);
        user_id = SharedPreferenceValue.getUserID(activity);
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
            startTimer(); ///star timer

        }
    }

    /// init view with proper question
    private void initViewWithQuestion() {
        resetOptions();
        tvStatus.setText("Played: " + String.valueOf(startingQuestionIndex + 1) + "/" + String.valueOf(endingQuestionIndex));
        tvQuestion.setText(question.getQuestion());

        tvOptionOne.setText(question.getOption_one());
        tvOptionTwo.setText(question.getOption_two());
        tvOptionThree.setText(question.getOption_three());
        tvOptionFour.setText(question.getOption_four());
    }

    /// reset all option tick icon
    void resetOptions() {

        ibtnOptionOneTick.setVisibility(View.GONE);
        ibtnOptionTwo.setVisibility(View.GONE);
        ibtnOptionThree.setVisibility(View.GONE);
        ibtnOptionFour.setVisibility(View.GONE);
       /* llOptionOne.setClickable(false);
        llOptionTwo.setClickable(false);
        llOptionThree.setClickable(false);
        llOptionFour.setClickable(false);*/

        btnRadioClicked = 0;
    }

    int btnRadioClicked = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llOptionOne:
                btnRadioClicked = 1;
                ibtnOptionOneTick.setVisibility(View.VISIBLE);
                ibtnOptionTwo.setVisibility(View.GONE);
                ibtnOptionThree.setVisibility(View.GONE);
                ibtnOptionFour.setVisibility(View.GONE);

                /*llOptionTwo.setClickable(false);
                llOptionThree.setClickable(false);
                llOptionThree.setClickable(false);*/


                break;
            case R.id.llOptionTwo:
                btnRadioClicked = 2;
                ibtnOptionTwo.setVisibility(View.VISIBLE);
                ibtnOptionOneTick.setVisibility(View.GONE);
                ibtnOptionThree.setVisibility(View.GONE);
                ibtnOptionFour.setVisibility(View.GONE);

                /*llOptionOne.setClickable(false);
                llOptionThree.setClickable(false);
                llOptionFour.setClickable(false);*/

                break;
            case R.id.llOptionThree:
                btnRadioClicked = 3;
                ibtnOptionThree.setVisibility(View.VISIBLE);
                ibtnOptionOneTick.setVisibility(View.GONE);
                ibtnOptionTwo.setVisibility(View.GONE);
                ibtnOptionFour.setVisibility(View.GONE);

                /*llOptionOne.setClickable(false);
                llOptionTwo.setClickable(false);
                llOptionFour.setClickable(false);*/

                break;
            case R.id.llOptionFour:
                btnRadioClicked = 4;
                ibtnOptionFour.setVisibility(View.VISIBLE);
                ibtnOptionOneTick.setVisibility(View.GONE);
                ibtnOptionTwo.setVisibility(View.GONE);
                ibtnOptionThree.setVisibility(View.GONE);

                /*llOptionOne.setClickable(false);
                llOptionTwo.setClickable(false);
                llOptionThree.setClickable(false);*/


                break;
            case R.id.ibtnTestPlayerBack:
                Intent intent = new Intent(activity, TestListActivity.class);
                intent.putExtra(StaticAccess.QUESTION_SET_ID, aTest.getQuestion_set_id());
                startActivity(intent);

                break;

            case R.id.btnNextQuestion:
                switch (btnRadioClicked) {
                    case 1:
                        checkCorrectAnswer(tvOptionOne.getText().toString());
                        break;
                    case 2:
                        checkCorrectAnswer(tvOptionTwo.getText().toString());
                        break;
                    case 3:
                        checkCorrectAnswer(tvOptionThree.getText().toString());
                        break;
                    case 4:
                        checkCorrectAnswer(tvOptionFour.getText().toString());
                        break;
                    default:
                        Toast.makeText(activity, "Select an option first!", Toast.LENGTH_SHORT).show();
                        break;
                }

                break;


        }
    }

    /// make all options clickable
    void makeAllClickAble() {
        llOptionOne.setClickable(true);
        llOptionTwo.setClickable(true);
        llOptionThree.setClickable(true);
        llOptionFour.setClickable(true);
    }

    /// check the user answer is correct or not
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

    /// check if test is over or not and go to next question by increasing the index
    void checkTestOver() {
        if (startingQuestionIndex == endingQuestionIndex - 1) {
            finishTheTest();
        } else {
            startingQuestionIndex++;
            question = questionsForPlay.get(startingQuestionIndex);
            makeAllClickAble();
            initViewWithQuestion();
        }
    }

    /// finish the test finally
    private void finishTheTest() {
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
        resIntent.putExtra(StaticAccess.TAG_USER_ID, user_id);
        resIntent.putExtra(StaticAccess.TEST_ID, test_id);
        startActivity(resIntent);
        finish();
    }

    //Declare timer
    CountDownTimer cTimer = null;

    //start timer function
    void startTimer() {
        long totalTime = Integer.parseInt(aTest.getTotal_time()) * 60 * 1000;
        cTimer = new CountDownTimer(totalTime, 1000) {
            public void onTick(long millisUntilFinished) {
                //tvTimer.setText("Remaining: " + ((millisUntilFinished / 1000) / 60) + " min");
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                tvTimer.setText("Time : " + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }

            public void onFinish() {
                checkTestOver();
            }
        };
        cTimer.start();
    }


    //cancel timer
    void cancelTimer() {
        if (cTimer != null)
            cTimer.cancel();
    }

    @Override
    protected void onDestroy() {
        cancelTimer();
        super.onDestroy();
    }
}
