package com.project.smurf.smurf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.String;

public class h_food_detail extends AppCompatActivity {

    private Button but_h_next;
    private TextView food_result;
    private TextView food_result_kcal;
    private TextView food_result_carb;
    private TextView food_result_fat;
    private TextView food_result_prot;
    private TextView food_result_sal;
    private TextView food_result_sug;
    private TextView food_result_allergy;
    private TextView food_result_ing;

    public JSONObject json_data;
    public JSONArray json_array;
    public String data = "";
    public String result_data = "";
    public JSONArray result_json_array;
    public JSONObject result_json_data;

    public String zz_name = " ";
    public String zz_kcal = " ";
    public String zz_carb = " ";
    public String zz_fat = " ";
    public String zz_prot = " ";
    public String zz_sal = " ";
    public String zz_sug = " ";
    public String zz_allergy = " ";
    public String zz_ing = " ";

    public String foodnames;

    public JSONTask jt;

    public String mpurl = "http://210.102.181.158:62003/json";
    //public String smurfurl = "http://192.9.20.62:62003/json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_food_detail);

        Intent intent = getIntent();
        foodnames = intent.getExtras().getString("foodname");
        jt = new JSONTask();

        jt.execute(mpurl);
        //json_data
        try {
            json_array = new JSONArray(foodnames);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        but_h_next = (Button) findViewById(R.id.but_h_next);
        but_h_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), History.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        food_result = (TextView) findViewById(R.id.food_result);
        food_result_kcal = (TextView) findViewById(R.id.food_result_kcal);
        food_result_carb = (TextView) findViewById(R.id.food_result_carb);
        food_result_fat = (TextView) findViewById(R.id.food_result_fat);
        food_result_prot = (TextView) findViewById(R.id.food_result_prot);
        food_result_sal = (TextView) findViewById(R.id.food_result_sal);
        food_result_sug = (TextView) findViewById(R.id.food_result_sug);
        food_result_allergy = (TextView) findViewById(R.id.food_result_allergy);
        food_result_ing = (TextView) findViewById(R.id.food_result_ing);

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
            // super.onPostExecute(result);

            try {
                result_json_array = new JSONArray(result);
                for (int i = 0; i < result_json_array.length(); i++) {
                    result_json_data = result_json_array.getJSONObject(i);
                    if (result_json_data.getString("food_name_kor").equals(foodnames)) {

                        zz_name += result_json_data.getString("food_name_kor");
                        zz_kcal += result_json_data.getString("food_kcal");
                        zz_carb += result_json_data.getString("food_carb");
                        zz_fat += result_json_data.getString("food_fat");
                        zz_prot += result_json_data.getString("food_prot");
                        zz_sal += result_json_data.getString("food_sal");
                        zz_sug += result_json_data.getString("food_sug");
                        zz_allergy += result_json_data.getString("food_allergy");
                        zz_ing += result_json_data.getString("food_ing");


                        //textView.setText(result_json_data.toString());
                       /* food_result.setText(result_json_data.getString("food_name_kor"));
                        food_result_kcal.setText(result_json_data.getString("food_kcal"));
                        food_result_carb.setText(result_json_data.getString("food_carb"));
                        food_result_fat.setText(result_json_data.getString("food_fat"));
                        food_result_prot.setText(result_json_data.getString("food_prot"));
                        food_result_sal.setText(result_json_data.getString("food_sal"));
                        food_result_sug.setText(result_json_data.getString("food_sug"));
                        food_result_allergy.setText(result_json_data.getString("food_allergy"));
                        food_result_ing.setText(result_json_data.getString("food_ing"));
                        */
                    }
                }
                food_result.setText(zz_name);
                food_result_kcal.setText(zz_kcal);
                food_result_carb.setText(zz_carb);
                food_result_fat.setText(zz_fat);
                food_result_prot.setText(zz_prot);
                food_result_sal.setText(zz_sal);
                food_result_sug.setText(zz_sug);
                food_result_allergy.setText(zz_allergy);
                food_result_ing.setText(zz_ing);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

