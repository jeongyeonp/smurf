package com.project.smurf.smurf;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class mypage extends AppCompatActivity {
    private Button mp_al;
    private TextView mpuser;
    private TextView mpallergy;

    private SessionHandler session;
    private JSONObject info;
    public String mp_url = "http://210.102.181.158:62003/mp/";
    public String user_url = "";

    public JSONTask mpinfo;
    public JSONArray result_array;
    public  JSONObject result_data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        mp_al = (Button) findViewById(R.id.mp_al);
        mp_al.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), mypage_allergy.class);
                startActivity(i);
                finish();
            }
        });

        mpuser = (TextView) findViewById(R.id.mpuser);
        mpallergy = (TextView) findViewById(R.id.mpallergy);

        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        info = new JSONObject();
        user_url = mp_url + user.getUser_id();

        mpuser.setText(user.getUser_id()+"님의 알러지 정보 : ");

        mpinfo = new JSONTask();
        mpinfo.execute(user_url);
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
                    mpallergy.append(result_array.getJSONObject(i).getString("allergy"));
                    mpallergy.append("\n");

                }
            }catch(JSONException e){
                e.printStackTrace();
            }

        }
    }
}

