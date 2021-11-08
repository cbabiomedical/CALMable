package com.example.calmable.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calmable.MusicModel;
import com.example.calmable.MusicPlayer;
import com.example.calmable.R;

import java.util.ArrayList;

public class HealingMusicAdapter extends RecyclerView.Adapter<HealingMusicAdapter.ViewHolder> {

    private ArrayList<MusicModel> listOfSongs;
    private Context context;

    public HealingMusicAdapter(ArrayList<MusicModel> listOfSongs, Context context) {
        this.listOfSongs = listOfSongs;
        this.context = context;
    }

    @NonNull
    @Override
    public HealingMusicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_name, parent, false);
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull HealingMusicAdapter.ViewHolder holder, int position) {
        holder.songTitle.setText(listOfSongs.get(position).getSongName());

        holder.songTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listOfSongs.get(position);

                Intent intent = new Intent(context, MusicPlayer.class);

                String songName = listOfSongs.get(position).getSongName();
                String url = listOfSongs.get(position).getUrl();
                intent.putExtra("songName", songName);
                intent.putExtra("url", url);

                Log.d("TAG", "song name : " + songName);
                Log.d("TAG", "url : " + url);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfSongs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView songTitle;
        RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songTitle = itemView.findViewById(R.id.songTitle);
            recyclerView = itemView.findViewById(R.id.listOfSongRecycleView);
        }
    }
}
