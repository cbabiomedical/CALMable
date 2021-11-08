package com.example.calmable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Home extends AppCompatActivity implements PopUpOne.PopUpOneListener {

    TextView txtHtRate;
    int StressLevel = 85;
    DeviceActivity deviceActivity;

    //private TextView textViewPerson;
    //private TextView textViewPlace;

    String viewPerson;
    String viewPlace;
    String dateAndTime;

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

        //textViewPerson = (TextView) findViewById(R.id.tag_person);
        //textViewPlace = (TextView) findViewById(R.id.tag_place);

        txtProgress.setText(String.valueOf(DeviceActivity.finalRate));
        String chr = getColoredSpanned(Integer.toString(DeviceActivity.finalRate), "#800000");
        String BPM = getColoredSpanned("\u1D2E\u1D3E\u1D39","#000000");
        txtProgress.setText(Html.fromHtml(chr+" "+BPM));

        Log.d("TAG", "home ht rate -->" + DeviceActivity.finalRate);

        //Chechikg the stress level (TODO: finalRate should be added here instead of StressLevel)
        if (StressLevel > 80) {
            openDialog();
        }


        NavigationBar();
    }

    @Override
    public void applyText(String person, String place) {
        //textViewPerson.setText(person);
        //textViewPlace.setText(place);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh:mm:ss a");
        String dateTime = simpleDateFormat.format(calendar.getTime());
        dateAndTime = dateTime;
        viewPerson = person;
        viewPlace = place;

        Log.d("Date And Time",dateAndTime);
        Log.d("Person Value",viewPerson);
        Log.d("Place Value",viewPlace);
    }

    //open are you stressed popup
    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        builder.setCancelable(true);
        builder.setMessage("Are You Stressed?");

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        //display getting user inputs popup
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Intent intent = new Intent(Home.this, UserInputPopup.class);
                //startActivity(intent);
                PopUpOne popUpOne = new PopUpOne();
                popUpOne.show(getSupportFragmentManager(),"popup one");
            }
        });
        builder.show();
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