package com.educareapps.quiz.parser;

import android.content.Context;

import com.educareapps.quiz.dao.CSVQuestionTable;
import com.educareapps.quiz.dao.LanguageTable;
import com.educareapps.quiz.dao.QuestionSetTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class QuizPlaceJson {

    Context context;
    String JSONValue;
    JSONArray jsonLanguageArray;
    JSONArray jsonQuestionSetArray;
    JSONArray jsonCSVQuestionArray;

    public static final String TAG_LANG_ID = "language_id";
    public static final String TAG_Language = "language";
    public static final String TAG_CREATED_AT = "created_at";

    public static final String TAG_QUESTION_SET_ID = "question_set_id";
    public static final String TAG_QUESTION_SET = "question_set";
    public static final String TAG_TITLE = "title";
    public static final String TAG_PHOTO = "photo";

    public static final String TAG_QUESTIONS = "questions";
    public static final String TAG_QUESTIONS_ID = "question_id";
    public static final String TAG_QUESTION = "question";
    public static final String TAG_OPTION_ONE = "option_one";
    public static final String TAG_OPTION_TWO = "option_two";
    public static final String TAG_OPTION_THREE = "option_three";
    public static final String TAG_OPTION_FOUR = "option_four";
    public static final String TAG_ANSWER = "answer";


    public QuizPlaceJson(Context context, JSONArray jsonLanguageArray) {
        this.context = context;
        this.jsonLanguageArray = jsonLanguageArray;

    }

    public void parser() {

        ArrayList<LanguageTable> languageArr = new ArrayList<>();

        try {
            if (jsonLanguageArray != null)
                for (int i = 0; i < jsonLanguageArray.length(); i++) {
                    JSONObject jresponse = jsonLanguageArray.getJSONObject(i);
                    LanguageTable languageTable = new LanguageTable();
                    languageTable.setLang_id(Long.parseLong(jresponse.getString(TAG_LANG_ID)));
                    languageTable.setLang_name(jresponse.getString(TAG_Language));
                    languageTable.setCreated_at(jresponse.getString(TAG_CREATED_AT));

                    jsonQuestionSetArray = jresponse.getJSONArray(TAG_QUESTION_SET);
                    ArrayList<QuestionSetTable> questionSetArr = new ArrayList<>();
                    if (jsonLanguageArray != null)
                        for (int j = 0; j < jsonQuestionSetArray.length(); j++) {
                            JSONObject questionSetJson = jsonQuestionSetArray.getJSONObject(j);
                            QuestionSetTable questionSetTable = new QuestionSetTable();
                            questionSetTable.setQuestion_set_id(Long.parseLong(questionSetJson.getString(TAG_QUESTION_SET_ID)));
                            questionSetTable.setQuestion_set(questionSetJson.getString(TAG_QUESTION_SET));
                            questionSetTable.setTitle(questionSetJson.getString(TAG_TITLE));
                            questionSetTable.setPhoto(questionSetJson.getString(TAG_PHOTO));
                            questionSetTable.setCreated_at(questionSetJson.getString(TAG_CREATED_AT));

                            jsonCSVQuestionArray = questionSetJson.getJSONArray(TAG_QUESTIONS);
                            ArrayList<CSVQuestionTable> CSVQuestionArr = new ArrayList<>();
                            if (jsonLanguageArray != null) {

                                for (int k = 0; k < questionSetJson.length(); k++) {
                                    JSONObject csvJson = jsonCSVQuestionArray.getJSONObject(k);
                                    CSVQuestionTable csvQuestionTable = new CSVQuestionTable();
                                    csvQuestionTable.setQuestion_id(Long.parseLong(csvJson.getString(TAG_QUESTIONS_ID)));
                                    csvQuestionTable.setQuestion(csvJson.getString(TAG_QUESTION));
                                    csvQuestionTable.setOption_one(csvJson.getString(TAG_OPTION_ONE));
                                    csvQuestionTable.setOption_two(csvJson.getString(TAG_OPTION_TWO));
                                    csvQuestionTable.setOption_three(csvJson.getString(TAG_OPTION_THREE));
                                    csvQuestionTable.setOption_four(csvJson.getString(TAG_OPTION_FOUR));
                                    csvQuestionTable.setAnswer(csvJson.getString(TAG_ANSWER));

                                    CSVQuestionArr.add(csvQuestionTable);
                                }
                            }
                            questionSetArr.add(questionSetTable);

                        }
                    languageArr.add(languageTable);
                }

        } catch (JSONException e) {
            e.printStackTrace();

        }

    }

}
