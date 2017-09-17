package com.educareapps.quiz.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
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
import com.educareapps.quiz.dao.UserTable;
import com.educareapps.quiz.manager.DatabaseManager;
import com.educareapps.quiz.utilities.AppController;
import com.educareapps.quiz.utilities.RootUrl;
import com.educareapps.quiz.utilities.SharedPreferenceValue;
import com.educareapps.quiz.utilities.StaticAccess;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    EditText etEmailUsername, etPassword;
    Button btnLogin, btnRegister, btnGmail;
    LoginButton loginButton;
    Button tvLink_signup, btnAlreadyRegistered;
    ProgressDialog progressDialog;
    LoginActivity activity;
    DatabaseManager databaseManager;
    TextView tvRegister, tvLogin;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;
        databaseManager = new DatabaseManager(activity);
        progressDialog = new ProgressDialog(activity);

        Typeface face = Typeface.createFromAsset(getAssets(), "font/sketch_book.ttf");

        etEmailUsername = (EditText) findViewById(R.id.etEmailUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        loginButton = (LoginButton) findViewById(R.id.loginButton);
        btnGmail = (Button) findViewById(R.id.btnGmail);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        tvLink_signup = (Button) findViewById(R.id.tvLink_signup);
        btnAlreadyRegistered = (Button) findViewById(R.id.btnAlreadyRegistered);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        tvLogin = (TextView) findViewById(R.id.tvLogin);

        tvRegister.setTypeface(face);
        tvLogin.setTypeface(face);

      /*  btnGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(activity, DashBoardActivity.class));
            }
        });*/


        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvLink_signup.setOnClickListener(this);
        btnAlreadyRegistered.setOnClickListener(this);
        btnGmail.setOnClickListener(this);

        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);


//        btnAlreadyRegistered.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btnLogin.setVisibility(View.VISIBLE);
//                tvLink_signup.setVisibility(View.VISIBLE);
//                btnAlreadyRegistered.setVisibility(View.GONE);
//                btnRegister.setVisibility(View.GONE);
//                /*loginButton.setVisibility(View.VISIBLE);
//                btnGmail.setVisibility(View.VISIBLE);*/
//
//                tvRegister.setVisibility(View.GONE);
//                tvLogin.setVisibility(View.VISIBLE);
//            }
//        });


        loginButton.setReadPermissions("email");
        //loginButton.se

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                String accessToken = loginResult.getAccessToken().getToken();
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject,
                                                    GraphResponse response) {


                                // Getting FB User Data
                                getFacebookData(jsonObject);


                            }
                        });


                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name,last_name,email,gender");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        //logger.logPurchase(BigDecimal.valueOf(4.32), Currency.getInstance("USD"));


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private void doRegistration(final String email, final String password, final String regType) {
        showProgress();
        StringRequest loginReq = new StringRequest(Request.Method.POST, RootUrl.REGISTRATION_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    String msg = "";
                    jsonObject = new JSONObject(response);
                    String svrResponse = jsonObject.getString(StaticAccess.TAG_USER_VALID_STATUS);
                    if (svrResponse.equals("error")) {
                        msg = jsonObject.getString(StaticAccess.TAG_RESPONSE_MSG);
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();

                    } else {


                        JSONObject user = jsonObject.getJSONObject(StaticAccess.USER_KEY_TAG);
                        int user_id = user.getInt("user_id");
                        String user_name = user.getString("user_name");
                        String user_first_name = user.getString("user_first_name");
                        String user_last_name = user.getString("user_first_name");
                        String email = user.getString("email");
                        String address = user.getString("address");
                        String occupation = user.getString("occupation");
                        String contact_no = user.getString("contact_no");
                        int status = user.getInt("status");
                        String datetime = new SimpleDateFormat("dd-mm-yyy").format(new Date());

                        UserTable aUser = new UserTable();
                        aUser.setUser_id(user_id);
                        aUser.setUser_name(user_name == null ? "" : user_name);
                        aUser.setUser_first_name(user_first_name == null ? "" : user_first_name);
                        aUser.setUser_last_name(user_last_name == null ? "" : user_last_name);
                        aUser.setEmail(email);
                        aUser.setAddress(address == null ? "" : address);
                        aUser.setOccupation(occupation == null ? "" : occupation);
                        aUser.setContact_no(contact_no == null ? "" : contact_no);
                        aUser.setStatus(String.valueOf(status));
                        aUser.setCreated_at(datetime);
                        databaseManager.insertUserTable(aUser);
                        Toast.makeText(activity, "Registration Successful", Toast.LENGTH_SHORT).show();
                        etEmailUsername.getText().clear();
                        etPassword.getText().clear();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                params.put("email", email);
                params.put("password", password);
                params.put("status", regType);
                return params;
            }

           /* @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                return params;
            }*/
        };
        AppController.getInstance().addToRequestQueue(loginReq);
    }

    private void doLogin(final String userNameEmail, final String password, final String userType) {
        showProgress();
        StringRequest loginReq = new StringRequest(Request.Method.POST, RootUrl.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    String svrResponse = jsonObject.getString(StaticAccess.TAG_USER_VALID_STATUS);
                    if (svrResponse.equals("error")) {
                        Toast.makeText(activity, "Wrong credential", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONObject user = jsonObject.getJSONObject(StaticAccess.USER_KEY_TAG);
                        int user_id = user.getInt("user_id");
                        UserTable userTable = databaseManager.getUserByServerUserID(user_id);
                        if (userTable == null) {
                            String user_name = user.getString("user_name");
                            String user_first_name = user.getString("user_first_name");
                            String user_last_name = user.getString("user_first_name");
                            String email = user.getString("email");
                            String address = user.getString("address");
                            String occupation = user.getString("occupation");
                            String contact_no = user.getString("contact_no");
                            int status = user.getInt("status");
                            String datetime = new SimpleDateFormat("dd-mm-yyy").format(new Date());
                            UserTable aUser = new UserTable();
                            aUser.setUser_id(user_id);
                            aUser.setUser_name(user_name == null ? "" : user_name);
                            aUser.setUser_first_name(user_first_name == null ? "" : user_first_name);
                            aUser.setUser_last_name(user_last_name == null ? "" : user_last_name);
                            aUser.setEmail(email);
                            aUser.setAddress(address == null ? "" : address);
                            aUser.setOccupation(occupation == null ? "" : occupation);
                            aUser.setContact_no(contact_no == null ? "" : contact_no);
                            aUser.setStatus(String.valueOf(status));
                            aUser.setCreated_at(datetime);
                            long id = databaseManager.insertUserTable(aUser);
                            SharedPreferenceValue.setUserID(activity, id);

                        } else {
                            SharedPreferenceValue.setUserID(activity, userTable.getId());
                        }
                        goToNextActivity();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                params.put("email", userNameEmail);
                params.put("password", password);
                params.put("regType", userType);
                return params;
            }

           /* @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                return params;
            }*/
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
                startActivity(new Intent(activity, DashBoardActivity.class));
                finish();
            }
        }, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                break;

            case R.id.btnGmail:
                break;

            case R.id.btnLogin:
                String emailUserName = etEmailUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (TextUtils.isEmpty(emailUserName)) {
                    etEmailUsername.setError("Field can not be empty !");
                } else if (TextUtils.isEmpty(password)) {
                    etPassword.setError("Field can not be empty !");

                } else {
                    doLogin(emailUserName, password, StaticAccess.KEY_NORMAL_REGISTRATION_TYPE);
                }
                break;

            case R.id.btnRegister:

                String email = etEmailUsername.getText().toString();
                String passwordReg = etPassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    etEmailUsername.setError("Field can not be empty !");
                } else if (TextUtils.isEmpty(passwordReg)) {
                    etPassword.setError("Field can not be empty !");
                } else {
                    doRegistration(email, passwordReg, StaticAccess.KEY_NORMAL_REGISTRATION_TYPE);
                }

                break;

            case R.id.tvLink_signup:

                btnLogin.setVisibility(View.GONE);
                tvLink_signup.setVisibility(View.GONE);
                btnAlreadyRegistered.setVisibility(View.VISIBLE);
                btnRegister.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.VISIBLE);
                btnGmail.setVisibility(View.VISIBLE);
                tvRegister.setVisibility(View.VISIBLE);
                tvLogin.setVisibility(View.GONE);

                break;

            case R.id.btnAlreadyRegistered:

                btnLogin.setVisibility(View.VISIBLE);
                tvLink_signup.setVisibility(View.VISIBLE);
                btnAlreadyRegistered.setVisibility(View.GONE);
                btnRegister.setVisibility(View.GONE);
                loginButton.setVisibility(View.VISIBLE);
                btnGmail.setVisibility(View.VISIBLE);

                tvRegister.setVisibility(View.GONE);
                tvLogin.setVisibility(View.VISIBLE);
                break;
        }
    }

    //faceBook
    private void getFacebookData(JSONObject object) {
//       Bundle bundle = new Bundle();
        String email = "";
        try {
            String id = object.getString("id");

            /*  URL profile_pic;
            try {
                profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?type=large");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }*/

     /*       bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));*/

            if (object.has("email"))
                email = object.getString("email");


            if (email.length() > 0) {

                doRegistration(email, "", StaticAccess.KEY_FACEBOOK_REGISTRATION_TYPE);
            }

        } catch (Exception e) {
            Log.d("tag", "BUNDLE Exception : " + e.toString());
        }
    }


}
