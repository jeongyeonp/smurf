package com.project.smurf.smurf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.AsyncTask;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.String;


public class foodinfo1 extends AppCompatActivity {
    private Button but_next;
    private Button f_confrim;
    private ImageView fv;
    private  TextView food_result;
    private  TextView food_result_kcal;
    private  TextView food_result_carb;
    private  TextView food_result_fat;
    private  TextView food_result_prot;
    private  TextView food_result_sal;
    private  TextView food_result_sug;
    private  TextView food_result_allergy;
    private  TextView food_result_ing;

    public JSONObject json_data;
    public JSONArray json_array;
    public String data = "";
    public String result_data="";
    public JSONArray result_json_array;
    public  JSONObject result_json_data;

    public String zz_name=" ";
    public String zz_kcal=" ";
    public String zz_carb=" ";
    public String zz_fat=" ";
    public String zz_prot=" ";
    public String zz_sal=" ";
    public String zz_sug=" ";
    public String zz_allergy=" ";
    public String zz_ing=" ";

    public JSONTask jt;

    public String smurfurl = "http://210.102.181.158:62003/";
    public String furl = "";
    public String vurl = "";
    //public String smurfurl = "http://192.9.20.62:62003/";

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private SessionHandler session;
    private JSONObject req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodinfo1);
        Intent intent = getIntent();
        String foodnames = intent.getExtras().getString("foodname");
        jt = new JSONTask();
        furl   = smurfurl+"json";
        jt.execute(furl);


        //json_data
        try {
            json_array = new JSONArray(foodnames);
            result_data = json_array.getJSONObject(0).getString("description");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        but_next = (Button) findViewById(R.id.but_next);
        but_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                savelog();
                startActivity(intent);
                finish();

            }
        });
        f_confrim=(Button)findViewById(R.id.f_confirm);
        f_confrim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), popup.class);
                intent.putExtra("foodname", zz_name);
                startActivity(intent);
            }
        });

        //foodview();
        food_result = (TextView)findViewById(R.id.food_result);
        food_result_kcal = (TextView)findViewById(R.id.food_result_kcal);
        food_result_carb = (TextView)findViewById(R.id.food_result_carb);
        food_result_fat = (TextView)findViewById(R.id.food_result_fat);
        food_result_prot = (TextView)findViewById(R.id.food_result_prot);
        food_result_sal = (TextView)findViewById(R.id.food_result_sal);
        food_result_sug = (TextView)findViewById(R.id.food_result_sug);
        food_result_allergy = (TextView)findViewById(R.id.food_result_allergy);
        food_result_ing = (TextView)findViewById(R.id.food_result_ing);



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
                result_json_array=new JSONArray(result);
                for(int i=0;i<result_json_array.length();i++){
                    result_json_data=result_json_array.getJSONObject(i);
                    if(result_json_data.getString("food_name_eng").equals(result_data)){

                        zz_name+= result_json_data.getString("food_name_kor");
                        zz_kcal+=result_json_data.getString("food_kcal");
                        zz_carb+=result_json_data.getString("food_carb");
                        zz_fat+=result_json_data.getString("food_fat");
                        zz_prot+=result_json_data.getString("food_prot");
                        zz_sal+=result_json_data.getString("food_sal");
                        zz_sug+=result_json_data.getString("food_sug");
                        zz_allergy+=result_json_data.getString("food_allergy");
                        zz_ing+=result_json_data.getString("food_ing");
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



            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }
    private void savelog() {
        req = new JSONObject();
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        try {
            req.put("history", zz_name.trim());
            req.put("user_id", user.getUser_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, furl, req, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject res) {
                        try {
                            //Check if user got registered successfully
                            if (res.getInt(KEY_STATUS) == 200) {
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
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }
    /*
    private void foodview(){
        fv = (ImageView)findViewById(R.id.foodview);

        req1 = new JSONObject();
        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        vurl = smurfurl+"v/"+user;
        try {
            req1.put("tf", zz_name.trim());
            req1.put("user_id", user.getUser_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, vurl, req1, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject res) {
                        try {
                            //Check if user got registered successfully
                            if (res.getInt(KEY_STATUS) == 200) {
                                fv.setImageResource(R.drawable.correct);

                            }
                            else if(res.getInt(KEY_STATUS) == 201){
                                fv.setImageResource(R.drawable.incorrect);
                            }
                            else{
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }
    */
}





