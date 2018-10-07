package com.project.smurf.smurf;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class mypage_allergy extends AppCompatActivity {

    private Button but_a_save;
    private Button but_a_del;
    private CheckBox rd1, rd2, rd3, rd4, rd5, rd6, rd7, rd8, rd9, rd10, rd11, rd12;
    private SessionHandler session;
    private JSONObject req;
    private ProgressDialog pDialog;
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private String ALLERGY = "x";
    //private String mp_url = "http://210.102.181.158:62003/mypage";
    private String mp_url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_allergy);
        //SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);

        but_a_save = (Button) findViewById(R.id.but_a_save);
        but_a_save.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(), mypage.class);
                uchecked();
                finish();
            }
        });
        but_a_del = (Button) findViewById(R.id.but_a_del);
        but_a_del.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(), mypage.class);
                udelete();
                finish();
            }
        });
        rd1 = (CheckBox) findViewById(R.id.rd1);rd2 = (CheckBox) findViewById(R.id.rd2);rd3 = (CheckBox) findViewById(R.id.rd3);
        rd4 = (CheckBox) findViewById(R.id.rd4);rd5 = (CheckBox) findViewById(R.id.rd5);rd6 = (CheckBox) findViewById(R.id.rd6);
        rd7 = (CheckBox) findViewById(R.id.rd7);rd8 = (CheckBox) findViewById(R.id.rd8);rd9 = (CheckBox) findViewById(R.id.rd9);
        rd10 = (CheckBox) findViewById(R.id.rd10);rd11 = (CheckBox) findViewById(R.id.rd11);rd12 = (CheckBox) findViewById(R.id.rd12);

        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        req = new JSONObject();

        rd1.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    try {
                        req.put("allergy", "난류");
                        req.put("user_id", user.getUser_id());
                    } catch (JSONException e) { e.printStackTrace(); } } }
        });
        rd2.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    try {
                        req.put("allergy", "돼지고기");
                        req.put("user_id", user.getUser_id());
                    } catch (JSONException e) { e.printStackTrace(); } } }
        });
        rd3.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    try {
                        req.put("allergy", "우유");
                        req.put("user_id", user.getUser_id());
                    } catch (JSONException e) { e.printStackTrace(); } } }
        });
        rd4.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    try {
                        req.put("allergy", "복숭아");
                        req.put("user_id", user.getUser_id());
                    } catch (JSONException e) { e.printStackTrace(); } } }
        });
        rd5.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    try {
                        req.put("allergy", "땅콩");
                        req.put("user_id", user.getUser_id());
                    } catch (JSONException e) { e.printStackTrace(); } } }
        });
        rd6.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    try {
                        req.put("allergy", "대두");
                        req.put("user_id", user.getUser_id());
                    } catch (JSONException e) { e.printStackTrace(); } } }
        });
        rd7.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    try {
                        req.put("allergy", "갑각류");
                        req.put("user_id", user.getUser_id());
                    } catch (JSONException e) { e.printStackTrace(); } } }
        });
        rd8.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    try {
                        req.put("allergy", "호두");
                        req.put("user_id", user.getUser_id());
                    } catch (JSONException e) { e.printStackTrace(); } } }
        });
        rd9.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    try {
                        req.put("allergy", "밀");
                        req.put("user_id", user.getUser_id());
                    } catch (JSONException e) { e.printStackTrace(); } } }
        });
        rd10.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    try {
                        req.put("allergy", "닭고기");
                        req.put("user_id", user.getUser_id());
                    } catch (JSONException e) { e.printStackTrace(); } } }
        });
        rd11.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    try {
                        req.put("allergy", "쇠고기");
                        req.put("user_id", user.getUser_id());
                    } catch (JSONException e) { e.printStackTrace(); } } }
        });
        rd12.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    try {
                        req.put("allergy", "조개류");
                        req.put("user_id", user.getUser_id());
                    } catch (JSONException e) { e.printStackTrace(); } } }
        });

    }

    private void uchecked() {
        //--------------------url--------------------
        mp_url = getString(R.string.smurfurl)+"/mypage";

        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, mp_url, req, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject res) {
                        try {
                            //Check if user got registered successfully
                            if (res.getInt(KEY_STATUS) == 200) {
                                loadDashboard();
                            } else {
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

    private void udelete(){
        mp_url = getString(R.string.smurfurl)+"/mypage/del";
        JsonObjectRequest jsArrayR = new JsonObjectRequest
                (Request.Method.POST, mp_url, req, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject res) {
                        try {
                            //Check if user got registered successfully
                            if (res.getInt(KEY_STATUS) == 200) {
                                loadDashboard();
                            } else {
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
        MySingleton.getInstance(this).addToRequestQueue(jsArrayR);
    }

    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), mypage.class);
        startActivity(i);
        finish();
    }
}
