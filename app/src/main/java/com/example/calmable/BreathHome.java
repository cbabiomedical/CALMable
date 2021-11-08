package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BreathHome extends AppCompatActivity {

    private Button level1Btn, level2Btn, level3Btn, level4Btn, level5Btn, level6Btn;
    private Prefs prefs;
    private Prefs2 prefs2;
    private Prefs3 prefs3;
    private Prefs4 prefs4;
    private Prefs5 prefs5;
    private Prefs6 prefs6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breath_home);

        prefs = new Prefs(this);
        prefs2 = new Prefs2(this);
        prefs3 = new Prefs3(this);
        prefs4 = new Prefs4(this);
        prefs5 = new Prefs5(this);
        prefs6 = new Prefs6(this);

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
        //if(prefs.getBreaths() == 0){
            //level2Btn.setClickable(false);
        //}else{
            level2Btn.setClickable(true);
            level2Btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(BreathHome.this, BreathLevel2.class);
                    startActivity(intent);

                }
            });
        //}

        //button to go to level 3
        level3Btn = findViewById(R.id.level3Btn);
        //if(prefs2.getBreaths() < 5){
            //level3Btn.setEnabled(false);
        //}else {
            level3Btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(BreathHome.this, BreathLevel3.class);
                    startActivity(intent);

                }
            });
        //}

        //button to go to level 4
        level4Btn = findViewById(R.id.level4Btn);
        //if(prefs3.getBreaths() < 5){
            //level4Btn.setEnabled(false);
        //}else {
            level4Btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(BreathHome.this, BreathLevel4.class);
                    startActivity(intent);

                }
            });
        //}

        //button to go to level 5
        level5Btn = findViewById(R.id.level5Btn);
        //if(prefs4.getBreaths() < 5){
            //level5Btn.setEnabled(false);
        //}else {
            level5Btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(BreathHome.this, BreathLevel5.class);
                    startActivity(intent);

                }
            });
        //}

        //button to go to level 6
        level6Btn = findViewById(R.id.level6Btn);
        //if(prefs5.getBreaths() < 5){
            //level6Btn.setEnabled(false);
        //}else {
            level6Btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(BreathHome.this, BreathLevel6.class);
                    startActivity(intent);

                }
            });
        //}

    }
}