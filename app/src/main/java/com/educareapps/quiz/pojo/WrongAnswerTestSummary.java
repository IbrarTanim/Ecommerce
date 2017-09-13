package com.educareapps.quiz.pojo;

import com.educareapps.quiz.dao.CSVQuestionTable;

import java.io.Serializable;

/**
 * Created by Rakib on 9/13/2017.
 */

public class WrongAnswerTestSummary implements Serializable {
    private CSVQuestionTable wrongQuestion;
    private int optionSelected;

    public WrongAnswerTestSummary(CSVQuestionTable wrongQuestion, int optionSelected) {
        this.wrongQuestion = wrongQuestion;
        this.optionSelected = optionSelected;
    }

    public WrongAnswerTestSummary() {
    }

    public CSVQuestionTable getWrongQuestion() {
        return wrongQuestion;
    }

    public void setWrongQuestion(CSVQuestionTable wrongQuestion) {
        this.wrongQuestion = wrongQuestion;
    }

    public int getOptionSelected() {
        return optionSelected;
    }

    public void setOptionSelected(int optionSelected) {
        this.optionSelected = optionSelected;
    }
}
