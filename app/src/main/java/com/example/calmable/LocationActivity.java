package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;
import com.example.calmable.PopUpOne;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Button currentLocation = (Button) findViewById(R.id.button1);

        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(PopUpOne.addArray.isEmpty()){
                    Toast.makeText(LocationActivity.this, "Stressed Locations Not found", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent in = new Intent(getApplicationContext(), MapsActivity.class);
                    startActivity(in);
                }
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
