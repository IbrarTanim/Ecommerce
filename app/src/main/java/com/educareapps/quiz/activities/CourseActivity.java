package com.educareapps.quiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;

import com.educareapps.quiz.R;
import com.educareapps.quiz.adapter.CourseAdapter;
import com.educareapps.quiz.dao.QuestionSetTable;
import com.educareapps.quiz.manager.DatabaseManager;

import java.util.ArrayList;

public class CourseActivity extends BaseActivity {

    GridView gvCourse;
    CourseAdapter courseAdapter;
    ArrayList<QuestionSetTable> questionSetTableArrayList;
    DatabaseManager databaseManager;
    CourseActivity activity;
    ImageButton ibtnBackCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        activity = this;
        gvCourse = (GridView) findViewById(R.id.gvCourse);
        ibtnBackCourse = (ImageButton) findViewById(R.id.ibtnBackCourse);
        databaseManager = new DatabaseManager(activity);
        questionSetTableArrayList = databaseManager.listQuestionSetTable();
        if (questionSetTableArrayList != null) {
            courseAdapter = new CourseAdapter(questionSetTableArrayList, activity);
            gvCourse.setAdapter(courseAdapter);

        }

        ibtnBackCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToPrevious();
            }
        });
    }

    private void backToPrevious() {
        startActivity(new Intent(activity, MainActivity.class));
        finish();
    }
}
