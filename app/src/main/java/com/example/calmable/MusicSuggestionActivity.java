package com.example.calmable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.calmable.adapter.ChillOutMusicAdapter;
import com.example.calmable.model.FavModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MusicSuggestionActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ChillOutMusicAdapter chillOutMusicAdapter;
    ArrayList<FavModel> listOfSongs;

    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_suggestion);


        recyclerView = findViewById(R.id.listOfSongRecycleView);

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        initData();

        getData();

    }

    private void initData() {

        listOfSongs = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Songs_Admin").child("Deep Relax");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postDataSnapshot : snapshot.getChildren()) {
                    FavModel post = postDataSnapshot.getValue(FavModel.class);
                    Log.d("Post", String.valueOf(post));
                    listOfSongs.add(post);
                }
                Log.d("List-->", String.valueOf(listOfSongs));


                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child("sugMusic");

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot datas : dataSnapshot.getChildren()) {
                            String id = datas.child("id").getValue().toString();
                            String msgType = datas.child("msgType").getValue().toString();

                            Log.d("TAG", "onDataChange: " + id);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                chillOutMusicAdapter = new ChillOutMusicAdapter(listOfSongs, getApplicationContext());
                recyclerView.setAdapter(chillOutMusicAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child("Deep Relax");
        reference.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<String> listOfID = new ArrayList<>();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    String friend = ds.getKey();
                    listOfID.add(friend);

                    Log.d("TAG", "key -----> : " + ds.getKey());

                    //String id = snapshot.child("id").getValue(String.class);
                    //String songName = snapshot.child("songName").getValue(String.class);

                    Log.d("TAG", "id -------------" + friend);
                    Log.d("TAG", "id -------------" + listOfID);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}