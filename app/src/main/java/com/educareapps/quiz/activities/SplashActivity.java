package com.educareapps.quiz.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.educareapps.quiz.R;
import com.educareapps.quiz.parser.QuizPlaceJson;
import com.educareapps.quiz.utilities.AppController;
import com.educareapps.quiz.utilities.RootUrl;

import org.json.JSONObject;

public class SplashActivity extends BaseActivity {

    SplashActivity activity;
    private int SPLASH_TIME_OUT = 2000;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activity = this;
        progressDialog = new ProgressDialog(activity);
        makeRequest();

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
                Toast.makeText(activity, String.valueOf(error.getMessage() + " we are starting again the request for you..."), Toast.LENGTH_SHORT).show();
                makeRequest();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

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


}
