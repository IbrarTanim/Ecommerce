package com.educareapps.quiz.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.educareapps.quiz.R;
import com.educareapps.quiz.activities.ResultActivity;
import com.educareapps.quiz.pojo.WrongAnswerTestSummary;
import com.educareapps.quiz.utilities.DialogNavBarHide;

import java.util.ArrayList;

/**
 * Created by Rakib on 9/13/2017.
 */

public class WrongQuestionsAdapter extends BaseAdapter {
    Context context;
    ArrayList<WrongAnswerTestSummary> wrongQuestionList;
    private static LayoutInflater inflater = null;
    ResultActivity activity;

    public WrongQuestionsAdapter(Context context, ArrayList<WrongAnswerTestSummary> wrongQuestionList) {
        this.context = context;
        this.wrongQuestionList = wrongQuestionList;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        activity = (ResultActivity) context;
    }

    @Override
    public int getCount() {
        return wrongQuestionList.size();
    }

    @Override
    public Object getItem(int position) {
        return wrongQuestionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return wrongQuestionList.get(position).getWrongQuestion().getId();
    }

    public class Holder {
        LinearLayout llOptionOneDisplay, llOptionTwoDisplay, llOptionThreeDisplay, llOptionFourDisplay;
        TextView tvQuestionDisplay, tvOptionOneDisplay, tvOptionTwoDisplay, tvOptionThreeDisplay, tvOptionFourDisplay;
        ImageButton ibtnOptionOneDisplay, ibtnOptionTwoDisplay, ibtnOptionThreeDisplay, ibtnOptionFourDisplay;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.wrong_cell, null);
        holder.tvQuestionDisplay = (TextView) rowView.findViewById(R.id.tvQuestionDisplay);

        holder.llOptionOneDisplay = (LinearLayout) rowView.findViewById(R.id.llOptionOneDisplay);
        holder.llOptionTwoDisplay = (LinearLayout) rowView.findViewById(R.id.llOptionTwoDisplay);
        holder.llOptionThreeDisplay = (LinearLayout) rowView.findViewById(R.id.llOptionThreeDisplay);
        holder.llOptionFourDisplay = (LinearLayout) rowView.findViewById(R.id.llOptionFourDisplay);

        holder.tvOptionOneDisplay = (TextView) rowView.findViewById(R.id.tvOptionOneDisplay);
        holder.tvOptionTwoDisplay = (TextView) rowView.findViewById(R.id.tvOptionTwoDisplay);
        holder.tvOptionThreeDisplay = (TextView) rowView.findViewById(R.id.tvOptionThreeDisplay);
        holder.tvOptionFourDisplay = (TextView) rowView.findViewById(R.id.tvOptionFourDisplay);


        holder.ibtnOptionOneDisplay = (ImageButton) rowView.findViewById(R.id.ibtnOptionOneDisplay);
        holder.ibtnOptionTwoDisplay = (ImageButton) rowView.findViewById(R.id.ibtnOptionTwoDisplay);
        holder.ibtnOptionThreeDisplay = (ImageButton) rowView.findViewById(R.id.ibtnOptionThreeDisplay);
        holder.ibtnOptionFourDisplay = (ImageButton) rowView.findViewById(R.id.ibtnOptionFourDisplay);

        holder.tvQuestionDisplay.setText(wrongQuestionList.get(position).getWrongQuestion().getQuestion());


        holder.tvQuestionDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFullQuestionDialog(wrongQuestionList.get(position).getWrongQuestion().getQuestion());
            }
        });

        holder.llOptionOneDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFullQuestionDialog(wrongQuestionList.get(position).getWrongQuestion().getOption_one());
            }
        });

        holder.llOptionTwoDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFullQuestionDialog(wrongQuestionList.get(position).getWrongQuestion().getOption_two());
            }
        });

        holder.llOptionThreeDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFullQuestionDialog(wrongQuestionList.get(position).getWrongQuestion().getOption_three());
            }
        });

        holder.llOptionFourDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFullQuestionDialog(wrongQuestionList.get(position).getWrongQuestion().getOption_four());
            }
        });


        holder.tvOptionOneDisplay.setText(wrongQuestionList.get(position).getWrongQuestion().getOption_one());
        holder.tvOptionTwoDisplay.setText(wrongQuestionList.get(position).getWrongQuestion().getOption_two());
        holder.tvOptionThreeDisplay.setText(wrongQuestionList.get(position).getWrongQuestion().getOption_three());
        holder.tvOptionFourDisplay.setText(wrongQuestionList.get(position).getWrongQuestion().getOption_four());

        holder.ibtnOptionOneDisplay.setVisibility(View.GONE);
        holder.ibtnOptionTwoDisplay.setVisibility(View.GONE);
        holder.ibtnOptionThreeDisplay.setVisibility(View.GONE);
        holder.ibtnOptionFourDisplay.setVisibility(View.GONE);
        /// select the correct answer first
        String answer = wrongQuestionList.get(position).getWrongQuestion().getAnswer().trim();
        String op1 = wrongQuestionList.get(position).getWrongQuestion().getOption_one().trim();
        String op2 = wrongQuestionList.get(position).getWrongQuestion().getOption_two().trim();
        String op3 = wrongQuestionList.get(position).getWrongQuestion().getOption_three().trim();
        String op4 = wrongQuestionList.get(position).getWrongQuestion().getOption_four().trim();
        if (op1.equalsIgnoreCase(answer)) {
            holder.ibtnOptionOneDisplay.setVisibility(View.VISIBLE);
            holder.ibtnOptionTwoDisplay.setVisibility(View.GONE);
            holder.ibtnOptionThreeDisplay.setVisibility(View.GONE);
            holder.ibtnOptionFourDisplay.setVisibility(View.GONE);
            op1 = "";

        } else if (op2.equalsIgnoreCase(answer)) {
            holder.ibtnOptionOneDisplay.setVisibility(View.GONE);
            holder.ibtnOptionTwoDisplay.setVisibility(View.VISIBLE);
            holder.ibtnOptionThreeDisplay.setVisibility(View.GONE);
            holder.ibtnOptionFourDisplay.setVisibility(View.GONE);
            op2 = "";

        } else if (op3.equalsIgnoreCase(answer)) {
            holder.ibtnOptionOneDisplay.setVisibility(View.GONE);
            holder.ibtnOptionTwoDisplay.setVisibility(View.GONE);
            holder.ibtnOptionThreeDisplay.setVisibility(View.VISIBLE);
            holder.ibtnOptionFourDisplay.setVisibility(View.GONE);
            op3 = "";

        } else if (op4.equalsIgnoreCase(answer)) {
            holder.ibtnOptionOneDisplay.setVisibility(View.GONE);
            holder.ibtnOptionTwoDisplay.setVisibility(View.GONE);
            holder.ibtnOptionThreeDisplay.setVisibility(View.GONE);
            holder.ibtnOptionFourDisplay.setVisibility(View.VISIBLE);
            op4 = "";

        }
        /// then show fucking users what they actually pressed for answer
        switch (wrongQuestionList.get(position).getOptionSelected()) {
            case 1:
                holder.ibtnOptionOneDisplay.setImageResource(R.drawable.wrong);
                holder.ibtnOptionOneDisplay.setVisibility(View.VISIBLE);
                break;
            case 2:
                holder.ibtnOptionTwoDisplay.setImageResource(R.drawable.wrong);
                holder.ibtnOptionTwoDisplay.setVisibility(View.VISIBLE);

                break;
            case 3:
                holder.ibtnOptionThreeDisplay.setImageResource(R.drawable.wrong);
                holder.ibtnOptionThreeDisplay.setVisibility(View.VISIBLE);
                break;
            case 4:
                holder.ibtnOptionFourDisplay.setImageResource(R.drawable.wrong);
                holder.ibtnOptionFourDisplay.setVisibility(View.VISIBLE);
                break;
        }

        return rowView;
    }

    public void showFullQuestionDialog(String text) {
        final Dialog dialog = new Dialog(activity, R.style.CustomAlertDialog);
        dialog.setContentView(R.layout.full_question_layout);
        dialog.setCancelable(true);

        TextView tvFullQuestion = (TextView) dialog.findViewById(R.id.tvFullQuestion);
        tvFullQuestion.setText(text);


        DialogNavBarHide.navBarHide(activity, dialog);
    }

}
