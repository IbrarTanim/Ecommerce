package com.educareapps.quiz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.educareapps.quiz.R;
import com.educareapps.quiz.dao.QuestionSetTable;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Rakib on 9/10/2017.
 */

public class CourseAdapter extends BaseAdapter {
    ArrayList<QuestionSetTable> questionSetTableArrayList;
    Context context;
    private static LayoutInflater inflater = null;

    public CourseAdapter(ArrayList<QuestionSetTable> questionSetTableArrayList, Context context) {
        this.questionSetTableArrayList = questionSetTableArrayList;
        this.context = context;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return questionSetTableArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return questionSetTableArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return questionSetTableArrayList.get(position).getQuestion_set_id();
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
        holder.tvCourseTitle.setText(questionSetTableArrayList.get(position).getTitle());
        Picasso.with(context).load(questionSetTableArrayList.get(position).getPhoto()).placeholder(R.drawable.ic_logo).error(R.drawable.ic_logo).into(holder.ivCoursePhoto);
        return rowView;
    }

}
