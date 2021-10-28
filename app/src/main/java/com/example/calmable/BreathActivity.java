package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BreathActivity extends AppCompatActivity {

    private Button breathLevelBtn1, breathLevelBtn2,breathLevelBtn3, breathLevelBtn4, breathLevelBtn5 ,breathLevelBtn6, breathLevelBtn7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breath);

        //"I'm stressed" button
        breathLevelBtn1 = findViewById(R.id.breathLevelBtn1);
        breathLevelBtn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BreathActivity.this, BreathLevel1.class);
                startActivity(intent);
                //startActivity(new Intent(getActivity(),breathLevel1.class));

            }
        });
    }
}