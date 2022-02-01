package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Sleep1DarkTheme extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep1_dark_theme);
    }

    //to change the dark theme to light theme
    public void btnTheme (View view){
        startActivity(new Intent(getApplicationContext(), SleepStory1.class));
    }

    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), SleepStoryReadActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}