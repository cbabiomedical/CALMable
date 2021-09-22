package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class SleepyActivity extends AppCompatActivity {

    private Button sleepStoryBtn, meditationBtn, soundscapeBtn;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleepy);

        sleepStoryBtn = findViewById(R.id.sleepStoryBtn);
        meditationBtn = findViewById(R.id.meditateBtn);
        soundscapeBtn = findViewById(R.id.soundscapeBtn);

        textView = findViewById(R.id.textView);

    }
}