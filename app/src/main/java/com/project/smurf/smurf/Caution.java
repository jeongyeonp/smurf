package com.project.smurf.smurf;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Caution extends AppCompatActivity {
    private TextView a_info;
    private TextView a_food_info;
    private SessionHandler session;
    private JSONObject info;
    public String mp_u_url = "http://210.102.181.158:62003/mypage/";
    public String user_info_url = "";

    public JSONTask allergyinfo;
    public JSONArray result_array;
    public  JSONObject result_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caution);

        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        info = new JSONObject();
        a_info = (TextView)findViewById(R.id.a_info);
        a_food_info = (TextView)findViewById(R.id.a_food_info);
        user_info_url = mp_u_url + user.getUser_id();

        allergyinfo = new JSONTask();
        allergyinfo.execute(user_info_url);
    }
    public class JSONTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            try {
                URL phpUrl = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection)phpUrl.openConnection();

                if ( conn != null ) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);

                    if ( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        while ( true ) {
                            String line = br.readLine();
                            if ( line == null )
                                break;
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
            return jsonHtml.toString();
        }
        @Override
        protected void onPostExecute(String result) {
            // super.onPostExecute(result);


            try{
                result_array=new JSONArray(result);
                for(int i=0;i<result_array.length();i++) {
                    a_info.append(result_array.getJSONObject(i).getString("allergy"));
                    a_food_info.append(result_array.getJSONObject(i).getString("food_name"));
                }
            }catch(JSONException e){
                e.printStackTrace();
            }

        }
    }
}

