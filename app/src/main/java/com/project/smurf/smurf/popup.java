package com.project.smurf.smurf;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class popup extends AppCompatActivity {
    private Button popbtn;
    private TextView popindex;
    private ImageView popview;
    private SessionHandler session;
    private JSONObject info;
    //public String p_url = "http://210.102.181.158:62003/mypage/";
    public String p_url = "";
    public String user_p_url = "";

    public JSONTask allergyinfo;
    public JSONArray json_array;
    public static String data = "";
    public String result_data = "";
    public JSONArray result_json_array;
    public JSONObject result_json_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_popup);

        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        info = new JSONObject();
        Intent intent = getIntent();
        result_data = intent.getExtras().getString("foodname");

        popbtn = (Button) findViewById(R.id.popbtn);
        popbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        //-----------------url-----------------
        p_url = getString(R.string.smurfurl)+"/mypage/";
        user_p_url = p_url + user.getUser_id();

        allergyinfo = new JSONTask();
        allergyinfo.execute(user_p_url);

        popview = (ImageView) findViewById(R.id.popview);
        popindex = (TextView) findViewById(R.id.popindex);

    }

    public class JSONTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            try {
                URL phpUrl = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) phpUrl.openConnection();

                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        while (true) {
                            String line = br.readLine();
                            if (line == null)
                                break;
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonHtml.toString();
        }

        @Override
        protected void onPostExecute(String result) {

            session = new SessionHandler(getApplicationContext());
            User user = session.getUserDetails();
            // super.onPostExecute(result)
            try {
                result_json_array = new JSONArray(result);
                for (int i = 0; i < result_json_array.length(); i++) {
                    result_json_data = result_json_array.getJSONObject(i);


                    if (result_json_data.getString("food_name").trim().equals(result_data.trim())) {
                        popview.setImageResource(R.drawable.incorrect);
                        popindex.setText(user.getUser_name() + "님 에게 위험한 음식입니다.");
                        break;
                    } else if (!result_json_data.getString("food_name").trim().equals(result_data.trim())) {
                        popview.setImageResource(R.drawable.correct);
                        popindex.setText(user.getUser_name() + "님 에게 안전한 음식입니다.");
                    } else {
                        popindex.setText("no food");
                    }


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        public boolean onTouchEvent(MotionEvent e) {
            if (e.getAction() == MotionEvent.ACTION_OUTSIDE) {
                return false;
            }
            return true;
        }

        public void onBackPressed() {
            return;
        }

    }
}
