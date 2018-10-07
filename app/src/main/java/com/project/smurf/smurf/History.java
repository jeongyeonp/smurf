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
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class History extends AppCompatActivity {

    private SessionHandler session;
    private JSONObject info;
    //public String h_url = "http://210.102.181.158:62003/his/";
    public String user_url = "";
    public String h_url = "";

    public JSONTask hisinfo;
    public JSONArray result_array;
    private ListView H_lv;
    private ArrayList<h_list> data =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        info = new JSONObject();
        //--------------url---------------------
        h_url = getString(R.string.smurfurl)+"/his/";
        user_url = h_url + user.getUser_id();

        H_lv =(ListView)findViewById(R.id.H_lv);
        H_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i =new Intent(getApplicationContext(),h_food_detail.class);
                i.putExtra("foodname", data.get(position).getFoodname());
                startActivity(i);
            }
        });

        hisinfo = new JSONTask();
        hisinfo.execute(user_url);
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
            data=new ArrayList<>();
            // super.onPostExecute(result);
            try{
                result_array=new JSONArray(result);
                for(int i=0;i<result_array.length();i++) {
                    //temphis.append(result_array.getJSONObject(i).getString("history"));
                    h_list fn = new h_list(result_array.getJSONObject(i).getString("history"));
                    data.add(fn);
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
            h_adapter adapter=new h_adapter(History.this, R.layout.list ,data);
            H_lv.setAdapter(adapter);

        }
    }
}


