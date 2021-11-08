package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SleepStoryReadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_story_read);
    }

    public void btnGoStoryAudio (View view){
        startActivity(new Intent(getApplicationContext(), SleepStoryAudioActivity.class));
    }
}