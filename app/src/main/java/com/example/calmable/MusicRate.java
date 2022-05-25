package com.example.calmable;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calmable.device.DeviceActivity;
import com.example.calmable.model.CalmChart;
import com.example.calmable.sample.JsonPlaceHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONArray;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MusicRate extends AppCompatActivity {

    Button ratingBtn, backBtn2;
    RatingBar ratingStars;
    float myRating = 0;
    public static float totalRating;
    int a;
    int c;
    String occupation;
    Double average = 0.0;
    Retrofit retrofit;
    JsonPlaceHolder jsonPlaceHolder;
    Double sum = 0.0;
    int x;
    int songTime;
    String url , songName ;

    FirebaseUser mUser;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_rate);

        ratingBtn = findViewById(R.id.ratingBtn);
        backBtn2 = findViewById(R.id.backBtn2);
        ratingStars = findViewById(R.id.ratingBar);
        SharedPreferences prefsTimeMem = getSharedPreferences("prefsMusic", MODE_PRIVATE);
        int firstStartTimeMem = prefsTimeMem.getInt("firstStartMusic", 0);


        mUser = FirebaseAuth.getInstance().getCurrentUser();


        //-----
        Bundle extras = getIntent().getExtras();

        url = extras.getString("url");
        songTime = extras.getInt("time");
        songName = extras.getString("songName");

        Log.d("TAG", "song---)>: " + url);
        Log.d("TAG", "song---)>: " + songName);



        ratingStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                int rating = (int) v;
                String message = null;

                myRating = ratingBar.getRating();

                switch (rating) {
                    case 1:
                        message = "Sorry to hear that!";
                        break;
                    case 2:
                        message = "We always accept suggestions";
                        break;
                    case 3:
                        message = "Good";
                        break;
                    case 4:
                        message = "Great! Thank you";
                        break;
                    case 5:
                        message = "Awesome!";
                        break;
                }
                Toast.makeText(MusicRate.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        Log.d("rating before click---------", String.valueOf(myRating));


        Calendar now = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Log.d("WEEK", String.valueOf(now.get(Calendar.WEEK_OF_MONTH)));
        Log.d("MONTH", String.valueOf(now.get(Calendar.MONTH)));
        Log.d("YEAR", String.valueOf(now.get(Calendar.YEAR)));
        Log.d("DAY", String.valueOf(now.get(Calendar.DAY_OF_MONTH)));

        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DAY_OF_MONTH) + 1;
        Format f = new SimpleDateFormat("EEEE");
        String str = f.format(new Date());
//prints day name
        System.out.println("Day Name: " + str);
        Log.d("Day Name", str);


        SharedPreferences sh = getSharedPreferences("prefsCount", MODE_APPEND);
        a = sh.getInt("firstStartCount", 0);
        Log.d("---------------a-------------", String.valueOf(a));

        int b = a + 1;

        SharedPreferences prefsCount1 = getSharedPreferences("prefsCount", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefsCount1.edit();
        editor.putInt("firstStartCount", b);
        editor.apply();
        SharedPreferences sha = getSharedPreferences("prefsCount", MODE_APPEND);

// The value will be default as empty string because for
// the very first time when the app is opened, there is nothing to show

        int a1 = sha.getInt("firstStartCount", 0);

        Log.d("A Count2", String.valueOf(a1));

        SharedPreferences sh1 = getSharedPreferences("prefsMusic", MODE_APPEND);
        x = sh1.getInt("firstStartMusic", 0);

        int y = x + 1;
        SharedPreferences prefsCount = getSharedPreferences("prefsMusic", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = prefsCount.edit();
        editor1.putInt("firstStartMusic", y);
        editor1.apply();
        SharedPreferences sha1 = getSharedPreferences("prefsMusic", MODE_APPEND);

        Log.d("CHECK", String.valueOf(DeviceActivity.musicRelaxation_index));

        Gson gson = new GsonBuilder().setLenient().create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(100, TimeUnit.SECONDS)
//                .readTimeout(100,TimeUnit.SECONDS).build();
        SharedPreferences prefsReport = getSharedPreferences("prefsReport", MODE_PRIVATE);
        int firstStartReport = prefsTimeMem.getInt("firstStartReport", 0);

        retrofit = new Retrofit.Builder().baseUrl("http://192.168.8.186:5000/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);

        JSONArray jsArray = new JSONArray(DeviceActivity.listOfTxtMusicReportData);
        JSONArray jsonArray = new JSONArray(DeviceActivity.listOfTxtReportData);
        Log.d("MusicTesting", String.valueOf(jsonArray));
        Log.d("MusicJson", String.valueOf(jsArray));
        Call<Object> call3 = jsonPlaceHolder.PostMusicReportData(jsArray);
        call3.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                Toast.makeText(getApplicationContext(), "Post Time Successful", Toast.LENGTH_SHORT).show();

                //Log.d(TAG, "-----onResponse-----: " + response);

                Log.d("TAG", "* reporttimem response code : " + response.code());
                Log.d("TAG", "reporttimem response message : " + response.message());
                Log.d("TAG", "reporttimem Relax index : " + response.body());
//                Log.d("musicType ", response.body().getClass().getSimpleName());p

                ArrayList list = new ArrayList();
                list = (ArrayList) response.body();
                LinkedTreeMap treeMap = new LinkedTreeMap();
                treeMap = (LinkedTreeMap) list.get(0);
                Log.d("TreeMap", String.valueOf(treeMap));


                // get time to relax index
                ArrayList listOfTimeToRelax = new ArrayList();
                listOfTimeToRelax = (ArrayList) response.body();
                LinkedTreeMap treeMapTimeToRelax = new LinkedTreeMap();
                treeMapTimeToRelax = (LinkedTreeMap) list.get(0);
                Double y = (Double) treeMap.get("time to relax");
                int timeToRelaxIndex = (int) Math.round(y);

                Log.d("TAG", "-------time to relax index-------" + timeToRelaxIndex);

                // calm chart
                HashMap<String, Object> calmChartData = new HashMap<>();
                calmChartData.put("songName", songName);
                calmChartData.put("songUrl", url);
                calmChartData.put("timeToRelax", timeToRelaxIndex);

                Log.d("TAG", "++++++++++++calm chart data+++++++++: " + calmChartData);


                // Saving Time Stressed and Relaxed in Firebase
                CalmChart calmChart = new CalmChart(songName,  url,  timeToRelaxIndex);


                FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).child("CalmChart").child(songName).setValue(calmChart)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                //Toast.makeText(UserPreferences.this, "Successful", Toast.LENGTH_SHORT).show();
                                //Intent intent=new Intent(UserPreferences.this,ProfileActivity.class);
                                //startActivity(intent);
                            }
                        });


                SharedPreferences sh = getSharedPreferences("prefsReport", MODE_APPEND);
                c = sh.getInt("firstStartReport", 0);
                Log.d("A Count", String.valueOf(x));

                int b = c + 1;

                SharedPreferences prefsCount1 = getSharedPreferences("prefsReport", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefsCount1.edit();
                editor.putInt("firstStartReport", b);
                editor.apply();

                // Saving Time Stressed and Relaxed in Firebase
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("TimeChart").child("Time Relaxed").child(mUser.getUid()).child(String.valueOf(now.get(Calendar.YEAR))).child(String.valueOf(month)).child(String.valueOf(now.get(Calendar.WEEK_OF_MONTH))).child("Monday").child(String.valueOf(c));
                reference.setValue(treeMap.get("time_relaxed"));
//                reference.setValue(new float[]{(float) treeMap.get("time_relaxed"), (float) treeMap.get("time_stressed")});
                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("TimeChart").child("Time Stressed").child(mUser.getUid()).child(String.valueOf(now.get(Calendar.YEAR))).child(String.valueOf(month)).child(String.valueOf(now.get(Calendar.WEEK_OF_MONTH))).child("Monday").child(String.valueOf(c));
                reference1.setValue(treeMap.get("time_stressed"));
//                reference.setValue(new float[]{(float) treeMap.get("time_relaxed"), (float) treeMap.get("time_stressed")});
                Log.d("CHECK", String.valueOf(treeMap.get("time_relaxed")));

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid()).child("occupation");
                LinkedTreeMap finalTreeMap = treeMap;
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String occupation = String.valueOf(snapshot.getValue());
                        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("WhereAmI").child("Time Stressed").child(occupation).child(String.valueOf(now.get(Calendar.YEAR))).child(String.valueOf(month)).child(String.valueOf(now.get(Calendar.WEEK_OF_MONTH))).child(str).child(mUser.getUid()).child(String.valueOf(c));
                        databaseReference1.setValue(finalTreeMap.get("time_stressed"));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                DatabaseReference referenceAge = FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid()).child("age");
                LinkedTreeMap finalHashmap = treeMap;
                referenceAge.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d("Ageof User", String.valueOf(snapshot.getValue()));
                        int age = Integer.parseInt(String.valueOf(snapshot.getValue()));


                        if (age >= 10 && age <= 20) {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("WhereAmI").child("Time Stressed").child("10-20").child(String.valueOf(now.get(Calendar.YEAR))).child(String.valueOf(month)).child(String.valueOf(now.get(Calendar.WEEK_OF_MONTH))).child(str).child(mUser.getUid()).child(String.valueOf(c));
                            reference.setValue(finalHashmap.get("time_stressed"));


                        } else if (age > 20 && age <= 30) {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("WhereAmI").child("Time Stressed").child("20-30").child(String.valueOf(now.get(Calendar.YEAR))).child(String.valueOf(month)).child(String.valueOf(now.get(Calendar.WEEK_OF_MONTH))).child(str).child(mUser.getUid()).child(String.valueOf(c));
                            reference.setValue(finalHashmap.get("time_stressed"));

                        } else if (age > 30 && age <= 40) {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("WhereAmI").child("Time Stressed").child("30-40").child(String.valueOf(now.get(Calendar.YEAR))).child(String.valueOf(month)).child(String.valueOf(now.get(Calendar.WEEK_OF_MONTH))).child(str).child(mUser.getUid()).child(String.valueOf(c));
                            reference.setValue(finalHashmap.get("time_stressed"));

                        } else if (age > 40 && age <= 50) {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("WhereAmI").child("Time Stressed").child("40-50").child(String.valueOf(now.get(Calendar.YEAR))).child(String.valueOf(month)).child(String.valueOf(now.get(Calendar.WEEK_OF_MONTH))).child(str).child(mUser.getUid()).child(String.valueOf(c));
                            reference.setValue(finalHashmap.get("time_stressed"));


                        } else if (age > 50 && age <= 60) {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("WhereAmI").child("Time Stressed").child("50-60").child(String.valueOf(now.get(Calendar.YEAR))).child(String.valueOf(month)).child(String.valueOf(now.get(Calendar.WEEK_OF_MONTH))).child(str).child(mUser.getUid()).child(String.valueOf(c));
                            reference.setValue(finalHashmap.get("time_stressed"));

                        } else if (age > 60 && age <= 70) {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("WhereAmI").child("Time Stressed").child("60-70").child(String.valueOf(now.get(Calendar.YEAR))).child(String.valueOf(month)).child(String.valueOf(now.get(Calendar.WEEK_OF_MONTH))).child(str).child(mUser.getUid()).child(String.valueOf(c));
                            reference.setValue(finalHashmap.get("time_stressed"));


                        } else if (age > 70 && age <= 80) {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("WhereAmI").child("Time Stressed").child("70-80").child(String.valueOf(now.get(Calendar.YEAR))).child(String.valueOf(month)).child(String.valueOf(now.get(Calendar.WEEK_OF_MONTH))).child(str).child(mUser.getUid()).child(String.valueOf(c));
                            reference.setValue(finalHashmap.get("time_stressed"));

                        } else if (age > 80 && age <= 90) {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("WhereAmI").child("Time Stressed").child("80-90").child(String.valueOf(now.get(Calendar.YEAR))).child(String.valueOf(month)).child(String.valueOf(now.get(Calendar.WEEK_OF_MONTH))).child(str).child(mUser.getUid()).child(String.valueOf(c));
                            reference.setValue(finalHashmap.get("time_stressed"));

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

            //
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed Time Post Relaxation", Toast.LENGTH_SHORT).show();
                Log.d("ErrorVal:Relaxation", String.valueOf(t));
                Log.d("TAG", "onFailure: " + t);

            }
        });

        PrintWriter writer;
        try {
            writer = new PrintWriter(getCacheDir() + "/ServerMusicReportData.txt");
            writer.print("");
            writer.close();
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < DeviceActivity.musicRelaxation_index.size(); i++) {
            sum += (Double) DeviceActivity.musicRelaxation_index.get(i);
        }

        average = sum / DeviceActivity.musicRelaxation_index.size();

        Double averageD = Double.valueOf(String.format("%.3g%n", average));
        Log.d("AverageD", String.valueOf(average));
        if (sum == 0.0) {
            averageD = 0.0;
        }
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ReportWW").child("MusicIntervention").child(mUser.getUid()).child(String.valueOf(now.get(Calendar.YEAR))).child(String.valueOf(month)).child(String.valueOf(now.get(Calendar.WEEK_OF_MONTH))).child(str).child(String.valueOf(x));
        reference.setValue(averageD);


        ratingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MusicRate.this, String.valueOf(myRating), Toast.LENGTH_SHORT).show();
                totalRating = totalRating + myRating;

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Report").child(mUser.getUid()).child("Music").child(String.valueOf(now.get(Calendar.YEAR)))
                        .child(String.valueOf(month)).child(String.valueOf(now.get(Calendar.WEEK_OF_MONTH))).child(str).child(String.valueOf(a));

                reference.setValue(myRating);

                Intent intent = new Intent(getApplicationContext(), MusicReportDaily.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


        backBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalRating = totalRating + myRating;

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Report").child(mUser.getUid()).child("Music").child(String.valueOf(now.get(Calendar.YEAR)))
                        .child(String.valueOf(month)).child(String.valueOf(now.get(Calendar.WEEK_OF_MONTH))).child(str).child(String.valueOf(a));
                reference.setValue(myRating);


                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

    }








    // for go back
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), Home.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}