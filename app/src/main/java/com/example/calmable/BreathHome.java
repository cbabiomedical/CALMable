package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BreathHome extends AppCompatActivity {

    private Button level1Btn, level2Btn, level3Btn, level4Btn, level5Btn, level6Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breath_home);
        //button to go to level 1
        level1Btn = findViewById(R.id.level1Btn);
        level1Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BreathHome.this, BreathLevel1.class);
                startActivity(intent);
                //startActivity(new Intent(getActivity(),breathLevel1.class));

            }
        });

        //button to go to level 2
        level2Btn = findViewById(R.id.level2Btn);
        level2Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BreathHome.this, BreathLevel2.class);
                startActivity(intent);
                //startActivity(new Intent(getActivity(),breathLevel1.class));

            }
        });

        //button to go to level 3
        level3Btn = findViewById(R.id.level3Btn);
        level3Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BreathHome.this, BreathLevel3.class);
                startActivity(intent);
                //startActivity(new Intent(getActivity(),breathLevel1.class));

            }
        });

        //button to go to level 4
        level4Btn = findViewById(R.id.level4Btn);
        level4Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BreathHome.this, BreathLevel4.class);
                startActivity(intent);
                //startActivity(new Intent(getActivity(),breathLevel1.class));

            }
        });

        //button to go to level 5
        level5Btn = findViewById(R.id.level5Btn);
        level5Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BreathHome.this, BreathLevel5.class);
                startActivity(intent);
                //startActivity(new Intent(getActivity(),breathLevel1.class));

            }
        });

        //button to go to level 6
        level6Btn = findViewById(R.id.level6Btn);
        level6Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BreathHome.this, BreathLevel6.class);
                startActivity(intent);
                //startActivity(new Intent(getActivity(),breathLevel1.class));

            }
        });

    }
}