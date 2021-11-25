package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Button currentLocation = (Button) findViewById(R.id.button1);

        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(in);
            }
        });

        }

//        public void btnCurrentLocation (View view){
//            startActivity(new Intent(this, MapsActivity.class));
//        }
//
//        public void btnRetrieveLocation (View view){
//            //startActivity(new Intent(getApplicationContext(), RetrieveMapsActivity.class));
//        }
    }
