package com.project.smurf.smurf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Date;
public class MainActivity extends AppCompatActivity {


    private ImageButton butCamera;
    private ImageButton butMp;
    private ImageButton butXfood;
    private ImageButton butHis;
    private TextView username;
    private Button logout;
    private SessionHandler session;

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backPressCloseHandler = new BackPressCloseHandler(this);

        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        username = (TextView)findViewById(R.id.username);
        username.setText(user.getUser_id());

       logout = (Button)findViewById(R.id.logout);
       logout.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               session.logoutUser();
               Intent i = new Intent(getApplicationContext(), login.class);
               startActivity(i);
               finish();
           }
       });



        butCamera = (ImageButton) findViewById(R.id.butCamera);
        butCamera.setOnClickListener(new View.OnClickListener() {

           public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
                finish();

            }
        });
        butMp = (ImageButton) findViewById(R.id.butMp);
        butMp.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), mypage_allergy.class);
                startActivity(intent);

            }
        });
        butXfood = (ImageButton) findViewById(R.id.butXfood);
        butXfood.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Caution.class);
                startActivity(intent);

            }
        });
        butHis = (ImageButton) findViewById(R.id.butHis);
        butHis.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), History.class);
                startActivity(intent);

            }
        });

    }
    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
        }
    }


