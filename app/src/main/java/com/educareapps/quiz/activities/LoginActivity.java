package com.educareapps.quiz.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.educareapps.quiz.R;
import com.educareapps.quiz.utilities.AppController;
import com.educareapps.quiz.utilities.RootUrl;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity {

    EditText etEmailUsername, etPassword;
    Button btnLogin;
    TextView tvLink_signup;
    ProgressDialog progressDialog;
    LoginActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;
        progressDialog = new ProgressDialog(activity);
        etEmailUsername = (EditText) findViewById(R.id.etEmailUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvLink_signup = (TextView) findViewById(R.id.tvLink_signup);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUserName = etEmailUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (TextUtils.isEmpty(emailUserName)) {
                    etEmailUsername.setError("Field can not be empty !");
                } else if (TextUtils.isEmpty(password)) {
                    etPassword.setError("Field can not be empty !");

                } else {
                    doLogin(emailUserName, password);
                }
            }
        });
    }

    private void doLogin(final String userNameEmail, final String password) {
        showProgress();
        StringRequest loginReq = new StringRequest(Request.Method.POST, RootUrl.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideProgress();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgress();
                Toast.makeText(activity, String.valueOf(error.getMessage()), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("usernameEmail", userNameEmail);
                params.put("password", password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(loginReq);

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

    private void goToNextActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(activity, MenuActivity.class));
                finish();
            }
        }, 1000);
    }
}