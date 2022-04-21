package com.example.calmable;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calmable.adapter.CalmChartAdapter;
import com.example.calmable.model.CalmChart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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


//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        listOfCalmChrt = new ArrayList<>();
//
//        calmChartAdapter = new CalmChartAdapter(this,listOfCalmChrt);


//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setHasFixedSize(true);
//        calmChartAdapter = new CalmChartAdapter(this , listOfCalmChrt);
//        recyclerView.setAdapter(calmChartAdapter);

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//
//                    CalmChart calmChart = dataSnapshot.getValue(CalmChart.class);
//                    listOfCalmChrt.add(calmChart);
//
//                    Log.d("TAG", "++++++++++++: " + listOfCalmChrt);
//
//                }
//
//                calmChartAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        initData();

        getDataId();

    }

    private void initData() {

        listOfCalmChrt = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid()).child("CalmChart");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    CalmChart calmChart = dataSnapshot.getValue(CalmChart.class);
                    listOfCalmChrt.add(calmChart);

                    Log.d("TAG", "++++++++++++: " + listOfCalmChrt);

                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid()).child("CalmChart");
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