package com.project.smurf.smurf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mypage extends AppCompatActivity {
    private Button mp_al;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        mp_al = (Button)findViewById(R.id.mp_al);
        mp_al.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), mypage_allergy.class);
                startActivity(i);
                finish();
            }
        });
    }
}
