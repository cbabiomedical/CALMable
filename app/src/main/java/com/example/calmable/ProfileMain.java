package com.example.calmable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);

        NavigationBar();

        getData();

        Button settings = (Button) findViewById(R.id.settings);
        Button signOutBtn = (Button) findViewById(R.id.signOut);
        Button myDownloadsBtn = (Button) findViewById(R.id.myDownloadsBtn);
        Button myFavouritesBtn = (Button) findViewById(R.id.myFavouritesBtn);
        Button remindersBtn = (Button) findViewById(R.id.remindersBtn);
        Button calenderBtn = (Button) findViewById(R.id.calenderBtn);
        Button premiumBtn = (Button) findViewById(R.id.premiumBtn);
        Button rateUsBtn = (Button) findViewById(R.id.rateUsBtn);
        Button aboutAppBtn = (Button) findViewById(R.id.aboutAppBtn);




        //go to edit profile page
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(in);
            }

        });

        myDownloadsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), MyDownloadsActivity.class);
                startActivity(in);
            }

        });

        myFavouritesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), MyFavouritesActivity.class);
                startActivity(in);
            }

        });

        remindersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), RemindersActivity.class);
                startActivity(in);
            }

        });

        calenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), CalenderActiivity.class);
                startActivity(in);
            }

        });

        premiumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), PremiumActivity.class);
                startActivity(in);
            }

        });

        rateUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), RateUsActivity.class);
                startActivity(in);
            }

        });

        aboutAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), AboutAppActivity.class);
                startActivity(in);
            }

        });

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), LoginUserActivity.class);
                FirebaseAuth.getInstance().signOut();
                startActivity(in);
                finish();

            }

        });

    }


    private void getData() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name = snapshot.child("fullName").getValue(String.class);

                Log.d("TAG", "edit profile: name-------------" + name);

                TextView textView = (TextView) findViewById(R.id.userName);
                textView.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }







    private void NavigationBar() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
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
                        return true;
                }

                return false;
            }
        });

    }
}