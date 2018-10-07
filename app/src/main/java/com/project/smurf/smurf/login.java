package com.project.smurf.smurf;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class login extends AppCompatActivity{

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_USERID = "id";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMPTY = "";
    private EditText txtId;
    private EditText txtPw;
    private String uid;
    private String upw;
    private ProgressDialog pDialog;
    //private String login_url = "http://210.102.181.158:62003/user_login";
    private String login_url = "";
    private SessionHandler session;

    private Button butRegist;
    private Button  butLogin;
    private Button  butIdPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());

        if(session.isLoggedIn()){
            loadDashboard();
        }
        setContentView(R.layout.activity_login);

        txtId = findViewById(R.id.txtId);
        txtPw = findViewById(R.id.txtPw);

        butRegist = (Button) findViewById(R.id.butRegist);
        butLogin = (Button) findViewById(R.id.butLogin);

        butLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid = txtId.getText().toString().toLowerCase().trim();
                upw = txtPw.getText().toString().trim();
                if (validateInputs()) {
                    login();
                }
            }
        });

        butRegist.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this, Registration.class);
                startActivity(i);
            }
        });
        butIdPw = (Button) findViewById(R.id.butIdPw);
        butIdPw.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Search_IDPW.class);
                startActivity(intent);
            }
        });
    }
    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
    private void displayLoader() {
        pDialog = new ProgressDialog(login.this);
        pDialog.setMessage("Logging In.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }
    private void login() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERID, uid);
            request.put(KEY_PASSWORD, upw);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //--------------url-------------------
        login_url = getString(R.string.smurfurl)+"/user_login";
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, login_url, request, response -> {
                    pDialog.dismiss();
                    try {
                        //Check if user got logged in successfully

                        if (response.getInt(KEY_STATUS)==200) {
                            session.loginUser(uid,response.getString(KEY_USERID));
                            loadDashboard();

                        }else{
                            Toast.makeText(getApplicationContext(),
                                    response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    private boolean validateInputs() {
        if(KEY_EMPTY.equals(uid)){
            txtId.setError("Id cannot be empty");
            txtId.requestFocus();
            return false;
        }
        if(KEY_EMPTY.equals(upw)){
            txtPw.setError("Password cannot be empty");
            txtPw.requestFocus();
            return false;
        }
        return true;
    }
}

