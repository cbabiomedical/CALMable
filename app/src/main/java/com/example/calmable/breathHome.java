package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class breathHome extends AppCompatActivity {
    private Button level1Btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breath_home);

        //"I'm stressed" button
        level1Btn = findViewById(R.id.level1Btn);
        level1Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(breathHome.this, breathLevel1.class);
                startActivity(intent);
                //startActivity(new Intent(getActivity(),breathLevel1.class));

            }
        });
    }
}