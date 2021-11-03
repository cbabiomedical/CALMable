package com.example.calmable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.calmable.device.DeviceActivity;
import com.example.calmable.scan.ScanActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    TextView txtHtRate;

    DeviceActivity deviceActivity;

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtHtRate = (TextView) findViewById(R.id.htRate);
        TextView txtProgress = (TextView) findViewById(R.id.txtProgress);
        TextView txtProgress2 = (TextView) findViewById(R.id.txtPastProgress);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar2);




        txtProgress.setText(String.valueOf(DeviceActivity.finalRate));
        String chr = getColoredSpanned(Integer.toString(DeviceActivity.finalRate), "#800000");
        String BPM = getColoredSpanned("\u1D2E\u1D3E\u1D39","#000000");
        txtProgress.setText(Html.fromHtml(chr+" "+BPM));


        Log.d("TAG", "home ht rate -->" + DeviceActivity.finalRate);

        NavigationBar();
    }


    private void NavigationBar() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        return true;
                    case R.id.relax:
                        startActivity(new Intent(getApplicationContext(), Relax.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.journal:
                        startActivity(new Intent(getApplicationContext(), Journal.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.challenge:
                        startActivity(new Intent(getApplicationContext(), Challenge.class));
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


    // landing page btn actions
    public void btnWatch (View view) {
        startActivity(new Intent(this, ScanActivity.class));
    }

    public void btnStressed (View view) {
        startActivity(new Intent(this, Relax.class));
    }

    public void btnSleepy (View view) {
        startActivity(new Intent(this, SleepyHome.class));
    }

    public void btnMotivate (View view) {
        startActivity(new Intent(this, MotivateHome.class));
    }

    public void btnHappy (View view) {
        startActivity(new Intent(this, HappyHome.class));
    }

    public void btnBreath (View view) {
        startActivity(new Intent(this, BreathHome.class));
    }

    public void btnReport (View view) {
        startActivity(new Intent(this, ReportHome.class));
    }


}