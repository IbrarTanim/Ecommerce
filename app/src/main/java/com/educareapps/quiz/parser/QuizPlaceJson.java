package com.educareapps.quiz.parser;

import android.content.Context;

import com.educareapps.quiz.dao.CSVQuestionTable;
import com.educareapps.quiz.dao.LanguageTable;
import com.educareapps.quiz.dao.QuestionSetTable;
import com.educareapps.quiz.dao.TestTable;
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
    JSONArray jsonTestArray;

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


    public static final String TAG_TEST_ARRAY = "tests";
    public static final String TAG_TEST_ID = "test_id";
    public static final String TAG_QUESTION_SET_ID_TEST = "question_set_id";
    public static final String TAG_TEST_NAME = "test_name";
    public static final String TAG_QUESTION_START_FROM = "question_start_from";
    public static final String TAG_QUESTION_START_TO = "question_start_to";
    public static final String TAG_STATUS = "status";
    public static final String TAG_CREATED_AT_TEST = "created_at";


    IDatabaseManager databaseManager;

    public QuizPlaceJson(Context context, JSONObject jsonObject) {
        this.context = context;
        this.jsonObject = jsonObject;
        databaseManager = new DatabaseManager(context);

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
                    long languageId = 0;
                    languageTable.setLang_id(Long.parseLong(quizObject.getString(TAG_LANG_ID)));
                    languageTable.setLang_name(quizObject.getString(TAG_LANGUAGE));
                    languageTable.setCreated_at(quizObject.getString(TAG_LANGUAGE_CREATED_AT));
                    jsonQuestionSetArray = quizObject.getJSONArray(TAG_QUESTION_SET);

                    languageId = databaseManager.insertLanguageTable(languageTable);
                    if (jsonQuestionSetArray != null)
                        for (int j = 0; j < jsonQuestionSetArray.length(); j++) {

                            JSONObject questionJsonObj = jsonQuestionSetArray.getJSONObject(j);
                            QuestionSetTable questionSetTable = new QuestionSetTable();
                            questionSetTable.setQuestion_set_id(Long.parseLong(questionJsonObj.getString(TAG_QUESTION_SET_ID)));
                            questionSetTable.setQuestion_set(questionJsonObj.getString(TAG_QUESTION_SET));
                            questionSetTable.setTitle(questionJsonObj.getString(TAG_QUESTION_TITLE));
                            questionSetTable.setPhoto(questionJsonObj.getString(TAG_QUESTION_PHOTO));
                            questionSetTable.setCreated_at(questionJsonObj.getString(TAG_LANGUAGE_CREATED_AT));
                            if (languageId > 0)
                                questionSetTable.setLang_id(languageId);

                            jsonCSVQuestionArray = questionJsonObj.getJSONArray(TAG_QUESTIONS_ARRAY);
                            long questionSetId = databaseManager.insertQuestionSetTable(questionSetTable);

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
                                    databaseManager.insertCSVQuestionTable(csvQuestionTable);
                                }
                            }

                            jsonTestArray = questionJsonObj.getJSONArray(TAG_TEST_ARRAY);
                            if (jsonTestArray != null) {
                                for (int m = 0; m < jsonTestArray.length(); m++) {
                                    JSONObject testJson = jsonTestArray.getJSONObject(m);
                                    TestTable testTable = new TestTable();
                                    testTable.setTest_id(Long.parseLong(testJson.getString(TAG_TEST_ID)));
                                    testTable.setQuestion_set_id(questionSetId);
                                    testTable.setTest_name(testJson.getString(TAG_TEST_NAME));
                                    testTable.setQuestion_start_from(testJson.getString(TAG_QUESTION_START_FROM));
                                    testTable.setQuestion_start_to(testJson.getString(TAG_QUESTION_START_TO));
                                    testTable.setStatus(testJson.getString(TAG_STATUS));
                                    testTable.setCreated_at(testJson.getString(TAG_CREATED_AT_TEST));
                                    databaseManager.insertTestTable(testTable);
                                }
                            }

                        }
                }
            isParseDone = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isParseDone;
    }
}
