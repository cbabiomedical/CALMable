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

    //Sleep story categories
    public void SleepStory1btn (View view){
        startActivity(new Intent(getApplicationContext(), SleepStory1.class));
    }
    public void SleepStory2btn (View view){
        startActivity(new Intent(getApplicationContext(), SleepStory2.class));
    }
    public void SleepStory3btn (View view){
        startActivity(new Intent(getApplicationContext(), SleepStory3.class));
    }
    public void SleepStory4btn (View view){
        startActivity(new Intent(getApplicationContext(), SleepStory4.class));
    }
    public void SleepStory5btn (View view){
        startActivity(new Intent(getApplicationContext(), SleepStory5.class));
    }
    public void SleepStory6btn (View view){
        startActivity(new Intent(getApplicationContext(), SleepStory6.class));
    }
}