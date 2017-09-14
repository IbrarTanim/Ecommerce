package com.educareapps.quiz.parser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.educareapps.quiz.activities.DashBoardActivity;
import com.educareapps.quiz.dao.LeaderBoardTable;
import com.educareapps.quiz.manager.DatabaseManager;
import com.educareapps.quiz.utilities.AppController;
import com.educareapps.quiz.utilities.DialogNavBarHide;
import com.educareapps.quiz.utilities.RootUrl;
import com.educareapps.quiz.utilities.SharedPreferenceValue;
import com.educareapps.quiz.utilities.StaticAccess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rakib on 9/12/2017.
 */

public class LeaderBoardUpdater {
    private static final String TAG_LEADERBOARD_ARRAY = "leaderboard_list";
    private static final String TAG_BOARD_ID = "board_id";
    private static final String TAG_USER_ID = "user_id";
    private static final String TAG_TEST_ID = "test_id";
    private static final String TAG_SCORE = "score";
    private static final String TAG_NEGATIVE = "negative";
    private static final String TAG_DURATION = "total_duration";
    private static final String TAG_HIGHSCORE = "isHighscore";
    private static final String TAG_CREATED_AT = "created_at";
    Context context;
    ProgressDialog progressDialog;

    public LeaderBoardUpdater(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);

    }

    boolean isUpdate = false;

    public boolean updateUserLeaderboard(final LeaderBoardTable leaderBoardTable) {
        showProgress();
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
                    isUpdate = false;
                }
                hideProgress();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isUpdate = false;
                hideProgress();
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

    JSONArray leaderBoardJsonArr;
    boolean isLeaderBoardSettingDone = false;

    public boolean settingLeaderBoardAsUser(final DashBoardActivity activity, final long user_id) {
        showProgress();
        final DatabaseManager databaseManager = new DatabaseManager(context);
        StringRequest insertLeaderBoardReq = new StringRequest(Request.Method.POST, RootUrl.GET_LEADER_BOARDS_FOR_USER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String svrResponse = jsonObject.getString(StaticAccess.TAG_USER_VALID_STATUS);
                    if (svrResponse.equals("error")) {
                        isLeaderBoardSettingDone = false;
                    } else {
                        leaderBoardJsonArr = jsonObject.getJSONArray(TAG_LEADERBOARD_ARRAY);
                        if (leaderBoardJsonArr != null) {
                            for (int k = 0; k < leaderBoardJsonArr.length(); k++) {
                                JSONObject lederBoardJson = leaderBoardJsonArr.getJSONObject(k);
                                LeaderBoardTable leaderBoard = new LeaderBoardTable();
                                leaderBoard.setBoard_id(Integer.parseInt(lederBoardJson.getString(TAG_BOARD_ID)));
                                long user_id = Long.parseLong(lederBoardJson.getString(TAG_USER_ID));
                                long test_id = Long.parseLong(lederBoardJson.getString(TAG_TEST_ID));
                                leaderBoard.setUser_id(user_id);
                                leaderBoard.setTest_id(test_id);
                                leaderBoard.setScore(Long.parseLong(lederBoardJson.getString(TAG_SCORE)));
                                leaderBoard.setTotal_duration(lederBoardJson.getString(TAG_DURATION));
                                leaderBoard.setNegative(lederBoardJson.getString(TAG_NEGATIVE));
                                leaderBoard.setIsHighscore(Boolean.parseBoolean(lederBoardJson.getString(TAG_HIGHSCORE)));
                                try {
                                    leaderBoard.setCreated_at(new SimpleDateFormat("mm-dd-yyy").parse(lederBoardJson.getString(TAG_CREATED_AT)));
                                } catch (ParseException e) {
                                    leaderBoard.setCreated_at(new Date());
                                }
                                LeaderBoardTable previousLeaderboard = databaseManager.getLeaderBoardByUserID(user_id, test_id);
                                if (previousLeaderboard == null) {
                                    databaseManager.insertLeaderBoardTable(leaderBoard);
                                }

                            }
                            isLeaderBoardSettingDone = true;
                            SharedPreferenceValue.setLeaderBoardOK(context, true);
                            activity.loadAllData();
                        }
                    }
                } catch (JSONException e) {
                    isLeaderBoardSettingDone = false;
                }
                hideProgress();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isLeaderBoardSettingDone = false;
                hideProgress();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", String.valueOf(user_id));

                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(insertLeaderBoardReq);
        return isLeaderBoardSettingDone;
    }


    private void showProgress() {
        if (progressDialog != null) {
            progressDialog.setMessage("please wait...");
            progressDialog.setCancelable(false);
            DialogNavBarHide.navBarHide((Activity) context, progressDialog);
        }

    }

    private void hideProgress() {

        if (progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }
}
