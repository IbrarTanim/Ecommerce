package com.educareapps.quiz.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.educareapps.quiz.R;
import com.educareapps.quiz.adapter.CorrectQuestionsAdapter;
import com.educareapps.quiz.adapter.WrongQuestionsAdapter;
import com.educareapps.quiz.dao.LeaderBoardTable;
import com.educareapps.quiz.manager.DatabaseManager;
import com.educareapps.quiz.parser.LeaderBoardUpdater;
import com.educareapps.quiz.pojo.CorrectAnswerTestSummary;
import com.educareapps.quiz.pojo.WrongAnswerTestSummary;
import com.educareapps.quiz.utilities.DialogNavBarHide;
import com.educareapps.quiz.utilities.InternetAvailabilityCheck;
import com.educareapps.quiz.utilities.StaticAccess;

import java.util.ArrayList;
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
    LeaderBoardUpdater leaderBoardUpdater;
    //// for getting correct answer & wrong answer question list
    ArrayList<CorrectAnswerTestSummary> correctQuestionList;
    ArrayList<WrongAnswerTestSummary> wrongQuestionList;
    LinearLayout lnCorrectAnsBlock, lnWrongAnsBlock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        activity = this;
        databaseManager = new DatabaseManager(activity);
        leaderBoardUpdater = new LeaderBoardUpdater(activity);
        totalPlayed = getIntent().getIntExtra(StaticAccess.TAG_TOTAL_PLAYED, -1);
        correctAnswer = getIntent().getIntExtra(StaticAccess.TAG_CORRECT_ANSWER, 0);
        wrongAnswer = getIntent().getIntExtra(StaticAccess.TAG_WRONG_ANSWER, 0);
        totalScore = getIntent().getIntExtra(StaticAccess.TAG_TOTAL_SCORE, 0);
        duration = getIntent().getStringExtra(StaticAccess.TAG_TOTAL_DURATION);
        comeFrom = getIntent().getIntExtra(StaticAccess.TAG_COME_FROM, -1);
        question_set_id = getIntent().getLongExtra(StaticAccess.QUESTION_SET_ID, -1);
        user_id = getIntent().getLongExtra(StaticAccess.TAG_USER_ID, -1);
        test_id = getIntent().getLongExtra(StaticAccess.TEST_ID, -1);
        correctQuestionList = (ArrayList<CorrectAnswerTestSummary>) getIntent().getSerializableExtra(StaticAccess.TAG_CORRECT_ANSWER_LIST);
        wrongQuestionList = (ArrayList<WrongAnswerTestSummary>) getIntent().getSerializableExtra(StaticAccess.TAG_WRONG_ANSWER_LIST);
        lnCorrectAnsBlock = (LinearLayout) findViewById(R.id.lnCorrectAnsBlock);
        lnWrongAnsBlock = (LinearLayout) findViewById(R.id.lnWrongAnsBlock);
        tvTotalPlayed = (TextView) findViewById(R.id.tvTotalPlayed);
        tvCorrectAnswer = (TextView) findViewById(R.id.tvCorrectAnswer);
        tvWrongAnswer = (TextView) findViewById(R.id.tvWrongAnswer);
        tvTotalScore = (TextView) findViewById(R.id.tvTotalScore);
        tvTotalDuration = (TextView) findViewById(R.id.tvTotalDuration);
        btnPlayAgain = (Button) findViewById(R.id.btnPlayAgain);
        ibtnCrossResult = (ImageButton) findViewById(R.id.ibtnCrossResult);

        btnPlayAgain.setOnClickListener(this);
        ibtnCrossResult.setOnClickListener(this);
        lnCorrectAnsBlock.setOnClickListener(this);
        lnWrongAnsBlock.setOnClickListener(this);
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
        leaderBoardTable.setCreated_at(new Date());
        long serverUserid = databaseManager.getUserTableById(user_id).getUser_id();
        long serverTestId = databaseManager.getTestTableById(test_id).getTest_id();
        LeaderBoardTable previousLeaderBoard = databaseManager.getLeaderBoardByUserID(databaseManager.getUserTableById(user_id).getUser_id(), databaseManager.getTestTableById(test_id).getTest_id());
        if (previousLeaderBoard == null) {
            /// for making server req we must need this two id to reset other wise server system may fucked up
            leaderBoardTable.setUser_id(serverUserid);
            leaderBoardTable.setTest_id(serverTestId);
            databaseManager.insertLeaderBoardTable(leaderBoardTable);
            if (InternetAvailabilityCheck.getConnectivityStatus(activity) != StaticAccess.TYPE_NOT_CONNECTED) {
                leaderBoardUpdater.updateUserLeaderboard(leaderBoardTable);

            } else {
                Toast.makeText(activity, "Leader board server update failed.Connect with internet then try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            leaderBoardTable.setId(previousLeaderBoard.getId());
            leaderBoardTable.setUser_id(serverUserid);
            leaderBoardTable.setTest_id(serverTestId);
            databaseManager.updateLeaderBoardtTable(leaderBoardTable);
            if (InternetAvailabilityCheck.getConnectivityStatus(activity) != StaticAccess.TYPE_NOT_CONNECTED) {
                leaderBoardUpdater.updateUserLeaderboard(leaderBoardTable);

            } else {
                Toast.makeText(activity, "Leader board server update failed.Connect with internet then try again", Toast.LENGTH_SHORT).show();
            }
        }
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
                    startActivity(new Intent(activity, DashBoardActivity.class));
                    finish();
                }
                break;
            case R.id.lnCorrectAnsBlock:
                if (correctQuestionList.size() > 0) {
                    showCorrectQuestionDialog();
                }
                break;
            case R.id.lnWrongAnsBlock:
                if (wrongQuestionList.size() > 0) {
                    showWrongQuestionDialog();
                }
                break;
        }

    }

    public void showCorrectQuestionDialog() {
        final Dialog dialog = new Dialog(activity, R.style.CustomAlertDialog);
        dialog.setContentView(R.layout.dialog_review_question);
        dialog.setCancelable(false);
        TextView tvQuestionListType = (TextView) dialog.findViewById(R.id.tvQuestionListType);
        tvQuestionListType.setText("Correct Question List");
        ImageButton ibtnTestPlayerBackDisplay = (ImageButton) dialog.findViewById(R.id.ibtnTestPlayerBackDisplay);
        ListView lvQuestionList = (ListView) dialog.findViewById(R.id.lvQuestionList);
        CorrectQuestionsAdapter correctQuestionsAdapter = new CorrectQuestionsAdapter(activity, correctQuestionList);
        lvQuestionList.setAdapter(correctQuestionsAdapter);
        ibtnTestPlayerBackDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        DialogNavBarHide.navBarHide(activity, dialog);
    }

    public void showWrongQuestionDialog() {
        final Dialog dialog = new Dialog(activity, R.style.CustomAlertDialog);
        dialog.setContentView(R.layout.dialog_review_question);
        dialog.setCancelable(false);
        TextView tvQuestionListType = (TextView) dialog.findViewById(R.id.tvQuestionListType);
        tvQuestionListType.setText("Wrong Question List");
        ImageButton ibtnTestPlayerBackDisplay = (ImageButton) dialog.findViewById(R.id.ibtnTestPlayerBackDisplay);
        ListView lvQuestionList = (ListView) dialog.findViewById(R.id.lvQuestionList);
        WrongQuestionsAdapter wrongQuestionsAdapter = new WrongQuestionsAdapter(activity, wrongQuestionList);
        lvQuestionList.setAdapter(wrongQuestionsAdapter);
        ibtnTestPlayerBackDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        DialogNavBarHide.navBarHide(activity, dialog);
    }

}
