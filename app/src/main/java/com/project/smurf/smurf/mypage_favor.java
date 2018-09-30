package com.project.smurf.smurf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mypage_favor extends AppCompatActivity {
    private Button but_fav_x;
    private Button but_fav_pre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_favor);

        but_fav_x = (Button) findViewById(R.id.but_fav_x);
        but_fav_x.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
            });
        but_fav_pre = (Button) findViewById(R.id.but_fav_pre);
        but_fav_pre.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), mypage_allergy.class);
            startActivity(intent);
        }
    });

    }
}

