package com.example.calmable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calmable.device.DeviceActivity;
import com.example.calmable.scan.ScanActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Home extends AppCompatActivity implements PopUpOne.PopUpOneListener {

    TextView txtHtRate;
    TextView txtProgress;
    int StressLevel = 85;
    File fileName;
    FirebaseUser mUser;
    StorageReference storageReference;

    //private TextView textViewPerson;
    //private TextView textViewPlace;

    String viewPerson;
    String viewPlace;
    String dateAndTime;
    int finalRateff;
    private Handler mHandler;

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtHtRate = (TextView) findViewById(R.id.htRate);
        txtProgress = (TextView) findViewById(R.id.txtProgress);
        TextView txtProgress2 = (TextView) findViewById(R.id.txtPastProgress);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        //textViewPerson = (TextView) findViewById(R.id.tag_person);
        //textViewPlace = (TextView) findViewById(R.id.tag_place);


        this.mHandler = new Handler();
        m_Runnable.run();

        //for testing
        finalRateff = 100;
        //Checking the stress level (TODO: finalRate should be added here instead of StressLevel)
        if (finalRateff > 80) {
            openDialog();
            Log.d("TAG", String.valueOf(finalRateff));
        }


        NavigationBar();
    }

    //refresh activity
    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            updateLandingHeartRate();
            //Toast.makeText(Home.this, "in runnable", Toast.LENGTH_SHORT).show();
            Home.this.mHandler.postDelayed(m_Runnable, 5000);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(m_Runnable);
        finish();

    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        try {
//            txtHtRate.setText(0);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }


    // show landing page heart rate
    public void updateLandingHeartRate() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.calmable", 0);
        finalRateff = sharedPreferences.getInt("heartRate", 0);
        txtProgress.setText(String.valueOf(finalRateff));
        String chr = getColoredSpanned(Integer.toString(finalRateff), "#800000");
        String BPM = getColoredSpanned("\u1D2E\u1D3E\u1D39", "#000000");
        txtProgress.setText(Html.fromHtml(chr + " " + BPM));
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

        HashMap<String, Object> Reports = new HashMap<>();
        List<Object> reportList = new ArrayList<>();
        reportList.add(dateAndTime);
        reportList.add(viewPerson);
        reportList.add(viewPlace);

        Log.d("----Array----", String.valueOf(reportList));

        //Writing data to file


        try {
            fileName = new File(getCacheDir() + "/reportStress.txt");
            //File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            fileName.createNewFile();
            if (!fileName.exists()) {
                fileName.mkdirs();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            int size = reportList.size();
            for (int i = 0; i < size; i++) {
                writer.write(reportList.get(i).toString());
                writer.newLine();
                writer.flush();

                Toast.makeText(this, "Data has been written to Report File", Toast.LENGTH_SHORT).show();
            }
            writer.close();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        // Uploading file created to firebase storage

        storageReference = FirebaseStorage.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser.getUid();

        StorageReference storageReference1 = storageReference.child("users/" + mUser.getUid());
        try {
            StorageReference mountainsRef = storageReference1.child("reportStress.txt");
            InputStream stream = new FileInputStream(new File(fileName.getAbsolutePath()));
            UploadTask uploadTask = mountainsRef.putStream(stream);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(ConcentrationReportDaily.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(ConcentrationReportDaily.this, "File Uploading Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        final Handler handler = new Handler();
        final int delay = 5000;

        //uploading reportList array values to firebase real time db
        Reports.put("Reports", reportList);
        FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).child("reportStress").child(dateAndTime).updateChildren(Reports)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Home.this, "Successful", Toast.LENGTH_SHORT).show();
                    }
                });
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