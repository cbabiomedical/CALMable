package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Sleep7DarkTheme extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep7_dark_theme);
    }

    //to change the dark theme to light theme
    public void btnTheme (View view){
        startActivity(new Intent(getApplicationContext(), SleepStory7.class));
    }
}