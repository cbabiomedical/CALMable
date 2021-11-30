package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MotivateHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivate_home);
    }


    public void btnGoSuccessStory (View view) {
        startActivity(new Intent(getApplicationContext() , SuccesStoryActivity.class));
    }

    public void btnGoCreativity (View view) {
        startActivity(new Intent(getApplicationContext() , SuccesStoryActivity.class));
    }

    public void btnGoSelfImpt (View view) {
        startActivity(new Intent(getApplicationContext() , SuccesStoryActivity.class));
    }
}