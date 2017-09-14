package com.educareapps.quiz.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.educareapps.quiz.R;
import com.educareapps.quiz.dao.CSVQuestionTable;
import com.educareapps.quiz.dao.LanguageTable;
import com.educareapps.quiz.dao.QuestionSetTable;
import com.educareapps.quiz.dao.TestTable;
import com.educareapps.quiz.manager.DatabaseManager;
import com.educareapps.quiz.manager.IDatabaseManager;
import com.educareapps.quiz.parser.QuizPlaceJson;
import com.educareapps.quiz.utilities.Animanation;
import com.educareapps.quiz.utilities.AppController;
import com.educareapps.quiz.utilities.CircularTextView;
import com.educareapps.quiz.utilities.DialogNavBarHide;
import com.educareapps.quiz.utilities.InternetAvailabilityCheck;
import com.educareapps.quiz.utilities.RootUrl;
import com.educareapps.quiz.utilities.SharedPreferenceValue;
import com.educareapps.quiz.utilities.StaticAccess;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class SplashActivity extends BaseActivity {

    SplashActivity activity;
    private int SPLASH_TIME_OUT = 2000;
    ProgressDialog progressDialog;
    IDatabaseManager databaseManager;
    CircularTextView ctvEduquiz;
    TextView tvPlus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activity = this;
        progressDialog = new ProgressDialog(activity);
        databaseManager = new DatabaseManager(activity);
        // if internet has
        checkPermission();

        Typeface face = Typeface.createFromAsset(getAssets(), "font/sketch_book.ttf");


        ctvEduquiz = (CircularTextView) findViewById(R.id.ctvEduquiz);
        ctvEduquiz.setTypeface(face);
        Animanation.zoomIn(ctvEduquiz);

        tvPlus = (TextView) findViewById(R.id.tvPlus);
        tvPlus.setTypeface(face);
        Animanation.rotationAnimation(tvPlus);
        ShimmerTextView tvshimmer = (ShimmerTextView) findViewById(R.id.tvshimmer);
        Shimmer shimmer = new Shimmer();
        shimmer.start(tvshimmer);
        printHashKey();

    }


    public void printHashKey() {
        try {
            //PackageInfo info = getPackageInfo(pContext, PackageManager.GET_SIGNATURES);
            PackageInfo info = getPackageManager().getPackageInfo("com.educareapps.quiz", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("HashKeyFind", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("HashKeyTest", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("HaskKeyCheck", "printHashKey()", e);
        }
    }


    private void goToNextActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SharedPreferenceValue.getUserID(activity) != -1) {
                    startActivity(new Intent(activity, DashBoardActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(activity, LoginActivity.class));
                    finish();
                }
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
                    SharedPreferenceValue.setDownloadSuccess(activity, true);
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

                                ArrayList<CSVQuestionTable> questionArr = new ArrayList<>(questionSetArr.get(j).getCsvQuestionToQuestionSet());
                                if (questionArr.size() > 0)
                                    for (int k = 0; k < questionArr.size(); k++) {

                                        databaseManager.deleteCSVQuestionById(questionArr.get(k).getId());
                                    }
                                ArrayList<TestTable> testTablesArr = new ArrayList<>(questionSetArr.get(j).getTestToQuestionSet());
                                if (testTablesArr.size() > 0)
                                    for (int k = 0; k < testTablesArr.size(); k++) {
                                        databaseManager.deleteTestById(testTablesArr.get(k).getId());
                                    }
                                databaseManager.deleteQuestionSetById(questionSetArr.get(j).getId());

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
            DialogNavBarHide.navBarHide(activity, progressDialog);
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
                if (!SharedPreferenceValue.getDownloadSuccess(activity)) {
                    if (InternetAvailabilityCheck.getConnectivityStatus(activity) != StaticAccess.TYPE_NOT_CONNECTED) {
                        new DeleteAsyncTask().execute();
                    } else {
                        Toast.makeText(activity, "Connect with internet then try again", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    goToNextActivity();
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
