package com.example.calmable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Relax extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relax);

        NavigationBar();
    }


    private void NavigationBar() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.relax);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.relax:
                        return true;
                    case R.id.journal:
                        startActivity(new Intent(getApplicationContext(), Journal.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.challenge:

                        ////////////////// edit
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileMain.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });

    }

    public void BtnGoDeepRelaxMusic(View view) {
        startActivity(new Intent(getApplicationContext(), DeepRelaxMusicActivity.class));
    }

    public void BtnGoNatureSounds(View view) {
        startActivity(new Intent(getApplicationContext(), NatureSoundsActivity.class));
    }

    public void BtnGoMeditateMusic(View view) {
        startActivity(new Intent(getApplicationContext(), MeditateMusicActivity.class));
    }

    public void BtnGoPainReliefMusic(View view) {
        startActivity(new Intent(getApplicationContext(), PainReliefMusicActivity.class));
    }

    public void BtnGoChillOutMusic(View view) {
        startActivity(new Intent(getApplicationContext(), ChillOutMusicActivity.class));
    }
}