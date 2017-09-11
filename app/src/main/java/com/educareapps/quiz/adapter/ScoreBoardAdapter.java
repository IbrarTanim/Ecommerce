package com.educareapps.quiz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.educareapps.quiz.R;
import com.educareapps.quiz.dao.LeaderBoardTable;
import com.educareapps.quiz.dao.TestTable;
import com.educareapps.quiz.manager.DatabaseManager;

import java.util.List;

/**
 * Created by Rakib on 9/10/2017.
 */

public class ScoreBoardAdapter extends BaseAdapter {
    List<LeaderBoardTable> scoreList;
    Context context;
    private static LayoutInflater inflater = null;
    private DatabaseManager databaseManager;

    public ScoreBoardAdapter(List<LeaderBoardTable> scoreList, Context context) {
        this.scoreList = scoreList;
        this.context = context;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        databaseManager = new DatabaseManager(context);

    }

    @Override
    public int getCount() {
        return scoreList.size();
    }

    @Override
    public Object getItem(int position) {
        return scoreList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return scoreList.get(position).getTest_id();
    }

    public class Holder {
        TextView tvCourseName, tvTestName, tvPercentage, tvDuration;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.score_item, null);
        holder.tvCourseName = (TextView) rowView.findViewById(R.id.tvCourseTitle);
        holder.tvTestName = (TextView) rowView.findViewById(R.id.tvTestName);
        holder.tvPercentage = (TextView) rowView.findViewById(R.id.tvPercentage);
        holder.tvDuration = (TextView) rowView.findViewById(R.id.tvDuration);
        TestTable testTable = databaseManager.getTestTableById(scoreList.get(position).getTest_id());

        holder.tvCourseName.setText(databaseManager.getQuestionSetTableById(testTable.getQuestion_set_id()).getTitle());
        holder.tvTestName.setText(testTable.getTest_name());
        int percent = ((int) scoreList.get(position).getScore() * 100) / (Integer.parseInt(testTable.getQuestion_start_from()) - Integer.parseInt(testTable.getQuestion_start_to()));
        holder.tvPercentage.setText(String.valueOf(percent + " %"));
        holder.tvDuration.setText(scoreList.get(percent).getTotal_duration());
        return rowView;
    }

}
