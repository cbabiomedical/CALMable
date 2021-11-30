package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class SleepyHome extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleepy_home);

    }

    public void btnGoSleepyStories (View view) {
        startActivity(new Intent(getApplicationContext(), SleepStoryAudioActivity.class));
    }

    public void btnGoMeditation (View view) {
        startActivity(new Intent(getApplicationContext(), MeditateMusicActivity.class));
    }

    public void btnGoSoundScape (View view) {
        startActivity(new Intent(getApplicationContext(), MeditateMusicActivity.class));
    }
}