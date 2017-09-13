package com.educareapps.quiz.pojo;

import com.educareapps.quiz.dao.CSVQuestionTable;

import java.io.Serializable;

/**
 * Created by Rakib on 9/13/2017.
 */

public class CorrectAnswerTestSummary implements Serializable {
    private CSVQuestionTable playedQuestion;
    private int optionSelected;

    public CorrectAnswerTestSummary(CSVQuestionTable playedQuestion, int optionSelected) {
        this.playedQuestion = playedQuestion;
        this.optionSelected = optionSelected;
    }

    public CorrectAnswerTestSummary() {
    }

    public CSVQuestionTable getPlayedQuestion() {
        return playedQuestion;
    }

    public void setPlayedQuestion(CSVQuestionTable playedQuestion) {
        this.playedQuestion = playedQuestion;
    }

    public int getOptionSelected() {
        return optionSelected;
    }

    public void setOptionSelected(int optionSelected) {
        this.optionSelected = optionSelected;
    }
}
