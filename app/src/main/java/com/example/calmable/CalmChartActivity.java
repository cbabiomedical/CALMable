package com.example.calmable;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calmable.adapter.CalmChartAdapter;
import com.example.calmable.adapter.MusicSuggestionAdapter;
import com.example.calmable.model.CalmChart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CalmChartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CalmChartAdapter calmChartAdapter;
    ArrayList<CalmChart> listOfCalmChrt;
    DatabaseReference databaseReference;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calm_chart);

        recyclerView = findViewById(R.id.listOfCalmChrtRecycleView);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid()).child("CalmChart");


        initData();

        getDataId();

    }

    private void initData() {

        listOfCalmChrt = new ArrayList<>();
        Query reference = FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid()).child("CalmChart")
                .orderByChild("timeToRelaxIndex");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    CalmChart calmChart = dataSnapshot.getValue(CalmChart.class);
                    listOfCalmChrt.add(calmChart);

                    for(int i = 0; i<listOfCalmChrt.size();i++)

                    Log.d("TAG", "calm chart data  : " + listOfCalmChrt);

                }

//                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                calmChartAdapter = new CalmChartAdapter(getApplicationContext(), listOfCalmChrt);
//                recyclerView.setAdapter(calmChartAdapter);

                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                mLayoutManager.setReverseLayout(true);
                mLayoutManager.setStackFromEnd(true);
                recyclerView.setLayoutManager(mLayoutManager);
                calmChartAdapter = new CalmChartAdapter(getApplicationContext(), listOfCalmChrt);
                recyclerView.setAdapter(calmChartAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //get songs id's
    private void getDataId() {

        Query reference = FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid()).child("CalmChart").orderByChild("timeToRelaxIndex");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<String> listOfID = new ArrayList<>();

                for (DataSnapshot postDataSnapshot : snapshot.getChildren()) {

                    String post = postDataSnapshot.child("songName").getValue(String.class);
                    Log.d("Post --> ", String.valueOf(post));
                    listOfID.add(String.valueOf(post));
                }
                Log.d("list of songs id's -->", String.valueOf(listOfID));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}