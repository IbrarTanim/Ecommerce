package com.educareapps.quiz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.educareapps.quiz.R;
import com.educareapps.quiz.dao.TestTable;

import java.util.ArrayList;

/**
 * Created by Rakib on 9/10/2017.
 */

public class TestsAdapter extends BaseAdapter {
    ArrayList<TestTable> testList;
    Context context;
    private static LayoutInflater inflater = null;

    public TestsAdapter(ArrayList<TestTable> testList, Context context) {
        this.testList = testList;
        this.context = context;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return testList.size();
    }

    @Override
    public Object getItem(int position) {
        return testList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return testList.get(position).getTest_id();
    }

    public class Holder {
        TextView tvCourseTitle;
        ImageView ivCoursePhoto;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.course_item, null);
        holder.tvCourseTitle = (TextView) rowView.findViewById(R.id.tvCourseTitle);
        holder.ivCoursePhoto = (ImageView) rowView.findViewById(R.id.ivCoursePhoto);
        holder.tvCourseTitle.setText(testList.get(position).getTest_name());
        holder.ivCoursePhoto.setImageResource(R.mipmap.ic_logo);
        holder.ivCoursePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTestActivity();
            }


        });
        return rowView;
    }

    private void goToTestActivity() {

    }
}
