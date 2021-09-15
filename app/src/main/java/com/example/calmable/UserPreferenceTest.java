package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserPreferenceTest extends AppCompatActivity {

    private LinearLayout linearLayout;
    private Button btn;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preference_test);


        linearLayout = findViewById(R.id.linearLayout2);
        linearLayout = findViewById(R.id.linearLayout3);
        linearLayout = findViewById(R.id.linearLayout4);
        linearLayout = findViewById(R.id.linearLayout5);
        linearLayout = findViewById(R.id.linearLayout6);
        linearLayout = findViewById(R.id.linearLayout7);
        linearLayout = findViewById(R.id.linearLayout8);
        btn = findViewById(R.id.buttonpf);
        tv = findViewById(R.id.textView3);

    }
}