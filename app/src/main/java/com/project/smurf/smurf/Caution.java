package com.project.smurf.smurf;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import android.widget.ListView;

public class Caution extends AppCompatActivity {
    private SessionHandler session;
    private JSONObject info;
    //public String mp_u_url = "http://210.102.181.158:62003/mypage/";
    public String user_info_url = "";

    public JSONTask allergyinfo;
    public JSONArray result_array;
    public  JSONObject result_data;

    private ListView C_LV;
    private ArrayList<c_list> data =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caution);

        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        info = new JSONObject();
        C_LV =(ListView)findViewById(R.id.C_LV);
        C_LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i =new Intent(getApplicationContext(),food_detail.class);
                 i.putExtra("foodname", data.get(position).getFoodname());
                 startActivity(i);
            }
        });
        //---------------url---------------
        user_info_url =  getString(R.string.smurfurl)+"/mypage/" + user.getUser_id();

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
            data=new ArrayList<>();
            try{
                result_array=new JSONArray(result);
                for(int i=0;i<result_array.length();i++) {
                    c_list fn = new c_list(result_array.getJSONObject(i).getString("food_name"));
                    data.add(fn);

                }
            }catch(JSONException e){
                e.printStackTrace();
            }
            c_adapter adapter=new c_adapter(Caution.this, R.layout.list ,data);
            C_LV.setAdapter(adapter);
        }
    }
}

