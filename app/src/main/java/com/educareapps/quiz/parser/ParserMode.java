/*
package com.educareapps.quiz.parser;

import android.content.Context;
import android.util.Log;

import com.educareapps.quiz.R;
import com.educareapps.quiz.activities.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;




public class ParserMode {
    Context context;
    String JSONValue;
    JSONArray jsonArray;
    //public ArrayList<Countries> linkList;
    // for avatar text Color static value
    public static final String TAG_COUNTRY_ID = "package_id";
    public static final String TAG_COUNTRY = "country";
    MainActivity mainActivity;

    public ParserMode(Context context, JSONArray jsonArray) {
        this.context = context;
        this.jsonArray = jsonArray;
        mainActivity = (MainActivity) context;
    }

    public void parser() {
        Log.d("JsonArray", jsonArray.toString());

        linkList = new ArrayList<>();
        try {
//            JSONArray jsonArray = new JSONArray(JSONValue);
            if (jsonArray != null)
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jresponse = jsonArray.getJSONObject(i);
                    mainActivity.addRadioStation((mainActivity.getString(R.string.international_radio)),jresponse.getString(TAG_COUNTRY),jresponse.getString(TAG_COUNTRY_ID));
                }
//            SharedPreferenceValue.setInsert(context,true);

        } catch (JSONException e) {
            e.printStackTrace();

        }


    }

}





*/
