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


public class Registration extends AppCompatActivity {

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_USERID = "id";
    private static final String KEY_EMPTY = "";
    private EditText reg_name;
    private EditText reg_Password;
    private EditText reg_ConfirmPassword;
    private EditText reg_phone;
    private EditText reg_email;
    private EditText reg_id;
    private String uname;
    private String uphone;
    private String uemail;
    private String uid;
    private String upassword;
    private String confirmPassword;
    private ProgressDialog pDialog;
   // private String register_url = "http://210.102.181.158:62003/user_register";
    private String register_url = "";
    private SessionHandler session;

    private Button btnRegisterLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        setContentView(R.layout.activity_registration);

        reg_name = findViewById(R.id.reg_name);
        reg_phone = findViewById(R.id.reg_phone);
        reg_email = findViewById(R.id.reg_email);
        reg_id = findViewById(R.id.reg_id);
        reg_Password = findViewById(R.id.reg_Password);
        reg_ConfirmPassword = findViewById(R.id.reg_ConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegisterLogin = (Button)findViewById(R.id.btnRegisterLogin);
        btnRegisterLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname = reg_name.getText().toString().trim();
                uphone = reg_phone.getText().toString().trim();
                uemail = reg_email.getText().toString().trim();
                uid = reg_id.getText().toString().trim();
                upassword = reg_Password.getText().toString().trim();
                confirmPassword = reg_ConfirmPassword.getText().toString().trim();
                if(validateInputs()){
                    uregister();
                }
            }
        });
    }
    private  void displayLoader(){
        pDialog = new ProgressDialog(Registration.this);
        pDialog.setMessage("Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }
    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), login.class);
        startActivity(i);
        finish();
    }
    private void uregister(){
        displayLoader();
        JSONObject req = new JSONObject();
        //--------url-------------
        register_url = getString(R.string.smurfurl)+"/user_register";
        try{
            req.put("name", uname);
            req.put("phone", uphone);
            req.put("email", uemail);
            req.put("id", uid);
            req.put("pw", upassword);

        }catch (JSONException e){
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, register_url, req, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject res) {
                        pDialog.dismiss();
                        try {
                            //Check if user got registered successfully
                            if (res.getInt(KEY_STATUS) == 200) {
                                //Set the user session
                                //session.loginUser(uid,res.getString(KEY_USERID));
                                loadDashboard();

                            }else if(res.getInt(KEY_STATUS) == 404){
                                //Display error message if username is already existsing
                                reg_id.setError("Username already taken!");
                                reg_id.requestFocus();

                            }else{
                                Toast.makeText(getApplicationContext(),
                                        res.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    private boolean validateInputs() {
        if (KEY_EMPTY.equals(uname)) {
            reg_name.setError("Name cannot be empty");
            reg_name.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(uid)) {
            reg_id.setError("ID cannot be empty");
            reg_id.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(upassword)) {
            reg_Password.setError("Password cannot be empty");
            reg_Password.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(confirmPassword)) {
            reg_ConfirmPassword.setError("Confirm Password cannot be empty");
            reg_ConfirmPassword.requestFocus();
            return false;
        }
        if (!upassword.equals(confirmPassword)) {
            reg_ConfirmPassword.setError("Password and Confirm Password does not match");
            reg_ConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }
}
