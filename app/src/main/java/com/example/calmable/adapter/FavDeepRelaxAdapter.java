package com.example.calmable.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calmable.R;
import com.example.calmable.db.FavDeepRelaxDB;
import com.example.calmable.model.FavDeepRelaxModel;

import java.util.List;

public class FavDeepRelaxAdapter extends RecyclerView.Adapter<FavDeepRelaxAdapter.ViewHolder> {

    private Context context;
    private List<FavDeepRelaxModel> favItemList;
    private FavDeepRelaxDB favDB;

    public FavDeepRelaxAdapter(Context context, List<FavDeepRelaxModel> favItemList) {
        this.context = context;
        this.favItemList = favItemList;
    }

    @NonNull
    @Override
    public FavDeepRelaxAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_name, parent, false);
        favDB = new FavDeepRelaxDB(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavDeepRelaxAdapter.ViewHolder holder, int position) {
        holder.favTextView.setText(favItemList.get(position).getItem_title());
        holder.favImageView.setImageResource(favItemList.get(position).getItem_image());
    }

    @Override
    public int getItemCount() {
        return favItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView favTextView;
        Button favBtn;
        ImageView favImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            favTextView = itemView.findViewById(R.id.songTitle);
            favBtn = itemView.findViewById(R.id.favHeartIcon);
            favImageView = itemView.findViewById(R.id.favImageView);

            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final FavDeepRelaxModel favItem = favItemList.get(position);
                    favDB.remove_fav_dp_music(favItem.getKey_id());
                    removeItem(position);

                }
            });
        }
    }
    private void removeItem(int position) {
        favItemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, favItemList.size());
    }
}
