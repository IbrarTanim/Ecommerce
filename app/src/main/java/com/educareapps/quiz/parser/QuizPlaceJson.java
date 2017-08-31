package com.educareapps.quiz.parser;

import android.content.Context;

import com.educareapps.quiz.dao.CSVQuestionTable;
import com.educareapps.quiz.dao.LanguageTable;
import com.educareapps.quiz.dao.QuestionSetTable;
import com.educareapps.quiz.manager.DatabaseManager;
import com.educareapps.quiz.manager.IDatabaseManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class QuizPlaceJson {

    Context context;
    String JSONValue;
    JSONObject jsonObject;
    JSONArray jsonQuestionSetArray;
    JSONArray jsonCSVQuestionArray;

    public static final String TAG_QUIZ_ARR = "quiz";
    public static final String TAG_LANG_ID = "language_id";
    public static final String TAG_LANGUAGE = "language";
    public static final String TAG_LANGUAGE_CREATED_AT = "created_at";

    public static final String TAG_QUESTION_SET_ID = "question_set_id";
    public static final String TAG_QUESTION_SET = "question_set";//array+object
    public static final String TAG_QUESTION_TITLE = "title";
    public static final String TAG_QUESTION_PHOTO = "photo";


    public static final String TAG_QUESTIONS_ARRAY = "questions";
    public static final String TAG_QUESTIONS_ID = "question_id";
    public static final String TAG_QUESTION = "question";
    public static final String TAG_QUESTIONS_OPTION_ONE = "option_one";
    public static final String TAG_QUESTIONS_OPTION_TWO = "option_two";
    public static final String TAG_QUESTIONS_OPTION_THREE = "option_three";
    public static final String TAG_QUESTIONS_OPTION_FOUR = "option_four";
    public static final String TAG_QUESTIONS_ANSWER = "answer";


    IDatabaseManager databaseManager;

    public QuizPlaceJson(Context context, JSONObject jsonObject) {
        this.context = context;
        this.jsonObject = jsonObject;
        databaseManager=new DatabaseManager(context);

    }

    boolean isParseDone = false;

    public boolean parser() {

        ArrayList<LanguageTable> languageArr = new ArrayList<>();

        try {
            JSONArray quizArrObj = jsonObject.getJSONArray(TAG_QUIZ_ARR);

            if (quizArrObj != null)
                for (int i = 0; i < quizArrObj.length(); i++) {
                    JSONObject quizObject = quizArrObj.getJSONObject(i);

                    LanguageTable languageTable = new LanguageTable();

                    long languageId=0;

                    languageTable.setLang_id(Long.parseLong(quizObject.getString(TAG_LANG_ID)));
                    languageTable.setLang_name(quizObject.getString(TAG_LANGUAGE));
                    languageTable.setCreated_at(quizObject.getString(TAG_LANGUAGE_CREATED_AT));
                    jsonQuestionSetArray = quizObject.getJSONArray(TAG_QUESTION_SET);

                    languageId=databaseManager.insertLanguageTable(languageTable);


                    if (jsonQuestionSetArray != null)
                        for (int j = 0; j < jsonQuestionSetArray.length(); j++) {

                            JSONObject questionJsonObj = jsonQuestionSetArray.getJSONObject(j);
                            QuestionSetTable questionSetTable = new QuestionSetTable();
                            questionSetTable.setQuestion_set_id(Long.parseLong(questionJsonObj.getString(TAG_QUESTION_SET_ID)));
                            questionSetTable.setQuestion_set(questionJsonObj.getString(TAG_QUESTION_SET));
                            questionSetTable.setTitle(questionJsonObj.getString(TAG_QUESTION_TITLE));
                            questionSetTable.setPhoto(questionJsonObj.getString(TAG_QUESTION_PHOTO));
                            questionSetTable.setCreated_at(questionJsonObj.getString(TAG_LANGUAGE_CREATED_AT));
                            if(languageId>0)
                            questionSetTable.setLang_id(languageId);

                            jsonCSVQuestionArray = questionJsonObj.getJSONArray(TAG_QUESTIONS_ARRAY);

                            long questionSetId=databaseManager.insertQuestionSetTable(questionSetTable);
                            if (jsonCSVQuestionArray != null) {
                                for (int k = 0; k < jsonCSVQuestionArray.length(); k++) {
                                    JSONObject csvJson = jsonCSVQuestionArray.getJSONObject(k);
                                    CSVQuestionTable csvQuestionTable = new CSVQuestionTable();
                                    csvQuestionTable.setQuestion_id(Long.parseLong(csvJson.getString(TAG_QUESTIONS_ID)));
                                    csvQuestionTable.setQuestion(csvJson.getString(TAG_QUESTION));


                                    csvQuestionTable.setQuestion_set_id(questionSetId);

                                    csvQuestionTable.setOption_one(csvJson.getString(TAG_QUESTIONS_OPTION_ONE));
                                    csvQuestionTable.setOption_two(csvJson.getString(TAG_QUESTIONS_OPTION_TWO));
                                    csvQuestionTable.setOption_three(csvJson.getString(TAG_QUESTIONS_OPTION_THREE));
                                    csvQuestionTable.setOption_four(csvJson.getString(TAG_QUESTIONS_OPTION_FOUR));
                                    csvQuestionTable.setAnswer(csvJson.getString(TAG_QUESTIONS_ANSWER));
                                    //db
                                    databaseManager.insertCSVQuestionTable(csvQuestionTable);
                                }

                            }
                            //db

                        }
                    //db

                }
            isParseDone = true;
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return isParseDone;
    }

}
