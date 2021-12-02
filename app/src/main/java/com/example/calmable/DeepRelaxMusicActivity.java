package com.example.calmable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.calmable.adapter.ChillOutMusicAdapter;
import com.example.calmable.adapter.DeepRelaxMusicAdapter;
import com.example.calmable.model.DeepRelaxModel;
import com.example.calmable.model.FavModel;
import com.example.calmable.model.MusicModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class DeepRelaxMusicActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DeepRelaxMusicAdapter deepRelaxMusicAdapter;
    ArrayList<FavModel> listOfSongs;

    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_relax_music);

        recyclerView = findViewById(R.id.listOfSongRecycleView);

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        listOfSongs = new ArrayList<>();

//        listOfSongs.add(new FavModel("0", "Test - Ours Saxmental-version", R.drawable.item_bg1,
//                "https://firebasestorage.googleapis.com/v0/b/calmableproject.appspot.com/o/Songs%2Fmusic-is-ours-saxmental-version.mp3?alt=media&token=379b9b63-d4a5-4968-bf3b-1a2aafa88b22", "0"));
//
//        listOfSongs.add(new FavModel("1", "Test - Lilac Days", R.drawable.item_bg2,
//                "https://firebasestorage.googleapis.com/v0/b/calmableproject.appspot.com/o/Songs%2Flilac-days.mp3?alt=media&token=5e3076ec-628e-4fcd-8591-2fc833c65c26", "0"));
//
        HashMap<String, Object> songs = new HashMap<>();
        songs.put("songList", listOfSongs);
        //DatabaseReference reference = FirebaseDatabase.getInstance().getReference("testMusic");
        //reference.setValue(songs);


        Log.d("List -> ", String.valueOf(listOfSongs));

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        deepRelaxMusicAdapter = new DeepRelaxMusicAdapter(listOfSongs, getApplicationContext());
        recyclerView.setAdapter(deepRelaxMusicAdapter);

        initData();
    }

    private void initData() {

        listOfSongs = new ArrayList<>();
        //DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Music").child("songList");
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

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                deepRelaxMusicAdapter = new DeepRelaxMusicAdapter(listOfSongs, getApplicationContext());
                recyclerView.setAdapter(deepRelaxMusicAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}