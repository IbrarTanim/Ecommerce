package com.educareapps.quiz.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import com.educareapps.quiz.R;
import com.educareapps.quiz.adapter.TestsAdapter;
import com.educareapps.quiz.dao.TestTable;
import com.educareapps.quiz.manager.DatabaseManager;
import com.educareapps.quiz.utilities.StaticAccess;

import java.util.ArrayList;

public class TestListActivity extends Activity {

    long question_set_id = -1;
    ArrayList<TestTable> testList;
    DatabaseManager databaseManager;
    TestListActivity activity;
    ImageButton ibtnBackTests;
    GridView gvTest;
    TestsAdapter testsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);
        activity = this;
        databaseManager = new DatabaseManager(activity);
        gvTest = (GridView) findViewById(R.id.gvTest);
        ibtnBackTests = (ImageButton) findViewById(R.id.ibtnBackTests);
        question_set_id = getIntent().getLongExtra(StaticAccess.QUESTION_SET_ID, -1);
        if (question_set_id != -1) {
            testList =  new ArrayList<TestTable>( databaseManager.getQuestionSetTableById(question_set_id).getTestToQuestionSet());
            if (testList != null) {
                testsAdapter = new TestsAdapter(testList, activity);
                gvTest.setAdapter(testsAdapter);
                gvTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startTestActivity(testList.get(position).getId());
                    }
                });

            }

            ibtnBackTests.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    backToPrevious();
                }
            });
        }
    }

    private void startTestActivity(Long id) {
        Intent testIntent = new Intent(activity, TestPlayerActivity.class);
        testIntent.putExtra(StaticAccess.TEST_ID, id);
        startActivity(testIntent);
        finish();
    }

    private void backToPrevious() {
        startActivity(new Intent(activity, CourseActivity.class));
        finish();
    }
}
