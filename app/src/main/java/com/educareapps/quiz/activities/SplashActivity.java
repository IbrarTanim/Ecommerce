package com.educareapps.quiz.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.educareapps.quiz.R;
import com.educareapps.quiz.dao.CSVQuestionTable;
import com.educareapps.quiz.dao.LanguageTable;
import com.educareapps.quiz.dao.QuestionSetTable;
import com.educareapps.quiz.manager.DatabaseManager;
import com.educareapps.quiz.manager.IDatabaseManager;
import com.educareapps.quiz.parser.QuizPlaceJson;
import com.educareapps.quiz.utilities.AppController;
import com.educareapps.quiz.utilities.InternetAvailabilityCheck;
import com.educareapps.quiz.utilities.RootUrl;
import com.educareapps.quiz.utilities.StaticAccess;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONObject;

import java.util.ArrayList;

public class SplashActivity extends BaseActivity {

    SplashActivity activity;
    private int SPLASH_TIME_OUT = 2000;
    ProgressDialog progressDialog;
    IDatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activity = this;
        progressDialog = new ProgressDialog(activity);
        databaseManager = new DatabaseManager(activity);
        // if internet has
        checkPermission();


        //new DeleteAsyncTask().execute();
        // else
        // go next
    }


    private void goToNextActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(activity, MenuActivity.class));
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void makeRequest() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, RootUrl.QuizUrl, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hideProgress();
                Log.e("Json", response.toString());
                QuizPlaceJson quizPlaceJson = new QuizPlaceJson(activity, response);
                boolean isDone = quizPlaceJson.parser();
                if (isDone) {
                    goToNextActivity();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgress();
                Toast.makeText(activity, String.valueOf(error.getMessage() + " we are starting again the request for you..."), Toast.LENGTH_SHORT).show();
                makeRequest();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

    }


    boolean isDelete = false;
    ArrayList<LanguageTable> languageArr;

    private class DeleteAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();

        }

        @Override
        protected String doInBackground(String... f_url) {
            try {
                languageArr = new ArrayList<>();
                languageArr = databaseManager.listLanguageTable();
                if (languageArr.size() > 0) {
                    for (int i = 0; i < languageArr.size(); i++) {

                        ArrayList<QuestionSetTable> questionSetArr = new ArrayList<>(languageArr.get(i).getQuestionSetToLanguage());
                        databaseManager.deleteLanguageById(languageArr.get(i).getId());
                        if (questionSetArr.size() > 0)
                            for (int j = 0; j < questionSetArr.size(); j++) {

                                ArrayList<CSVQuestionTable> questionArr = new ArrayList<>(questionSetArr.get(i).getQuestionSetToLanguage());
                                databaseManager.deleteQuestionSetById(questionSetArr.get(i).getId());

                                if (questionArr.size() > 0)
                                    for (int k = 0; k < questionArr.size(); k++) {

                                        databaseManager.deleteQuestionById(questionArr.get(i).getId());

                                    }

                            }
                    }
                }


                isDelete = true;
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
                isDelete = false;
            }
            return null;
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(String file_url) {
            if (isDelete) {
                makeRequest();
            } else
                hideProgress();
        }
    }

    private void showProgress() {
        if (progressDialog != null) {
            progressDialog.setMessage("please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

    }

    private void hideProgress() {

        if (progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

    boolean isAllPermissionGranted = false;

    void checkPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                isAllPermissionGranted = true;
                if (InternetAvailabilityCheck.getConnectivityStatus(activity) != StaticAccess.TYPE_NOT_CONNECTED) {
                    new DeleteAsyncTask().execute();
                }
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(activity, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                checkPermission();
            }
        };
        new TedPermission(this)

                .setPermissionListener(permissionlistener)
                .setRationaleMessage("we need permission for RECORD_AUDIO, READ_EXTERNAL_STORAGE,& WRITE_EXTERNAL_STORAGE & INTERNET")
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setGotoSettingButtonText("setting")
                .setPermissions(Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE)
                .check();

    }

}
