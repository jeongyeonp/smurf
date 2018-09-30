package com.project.smurf.smurf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Search_IDPW extends AppCompatActivity {

    private Button button_id;
    private Button button_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__idpw);

        button_id = (Button) findViewById(R.id.button_id);
        button_id.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchID.class);
                startActivity(intent);
            }
        });

        button_pw = (Button) findViewById(R.id.button_pw);
        button_pw.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchPW.class);
                startActivity(intent);
            }
        });


    }
}
