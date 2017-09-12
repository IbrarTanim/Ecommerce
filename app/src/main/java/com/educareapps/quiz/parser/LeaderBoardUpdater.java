package com.educareapps.quiz.parser;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.educareapps.quiz.dao.LeaderBoardTable;
import com.educareapps.quiz.utilities.AppController;
import com.educareapps.quiz.utilities.RootUrl;
import com.educareapps.quiz.utilities.StaticAccess;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rakib on 9/12/2017.
 */

public class LeaderBoardUpdater {
    Context context;

    public LeaderBoardUpdater(Context context) {
        this.context = context;
    }

    boolean isUpdate = false;

    public boolean updateUserLeaderboard(final LeaderBoardTable leaderBoardTable) {
        StringRequest updateLeaderBoardReq = new StringRequest(Request.Method.POST, RootUrl.UPDATE_LEADER_BOARD_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String svrResponse = jsonObject.getString(StaticAccess.TAG_USER_VALID_STATUS);
                    if (svrResponse.equals("error")) {
                        isUpdate = false;
                    } else {
                        isUpdate = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    isUpdate = false;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isUpdate = false;
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", String.valueOf(leaderBoardTable.getUser_id()));
                params.put("test_id", String.valueOf(leaderBoardTable.getTest_id()));
                params.put("score", String.valueOf(leaderBoardTable.getScore()));
                params.put("total_duration", leaderBoardTable.getTotal_duration());
                params.put("negative", leaderBoardTable.getNegative());
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(updateLeaderBoardReq);
        return isUpdate;
    }

    boolean isInserted = false;

    public boolean insertUserLeaderboard(final LeaderBoardTable leaderBoardTable) {
        StringRequest insertLeaderBoardReq = new StringRequest(Request.Method.POST, RootUrl.INSERT_LEADER_BOARD_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String svrResponse = jsonObject.getString(StaticAccess.TAG_USER_VALID_STATUS);
                    if (svrResponse.equals("error")) {
                        isInserted = false;
                    } else {
                        isInserted = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    isInserted = false;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isInserted = false;
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", String.valueOf(leaderBoardTable.getUser_id()));
                params.put("test_id", String.valueOf(leaderBoardTable.getTest_id()));
                params.put("score", String.valueOf(leaderBoardTable.getScore()));
                params.put("total_duration", leaderBoardTable.getTotal_duration());
                params.put("negative", leaderBoardTable.getNegative());
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(insertLeaderBoardReq);
        return isInserted;
    }
}
