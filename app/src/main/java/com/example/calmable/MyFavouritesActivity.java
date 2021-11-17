package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.calmable.adapter.ChillOutMusicAdapter;
import com.example.calmable.adapter.DeepRelaxMusicAdapter;
import com.example.calmable.adapter.FavAdapter;
import com.example.calmable.adapter.FavDeepRelaxAdapter;
import com.example.calmable.db.FavDB;
import com.example.calmable.db.FavDeepRelaxDB;
import com.example.calmable.model.DeepRelaxModel;
import com.example.calmable.model.FavModel;

import java.util.ArrayList;

public class MyFavouritesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<FavModel> favouriteList;
    private FavDB favDB;
    FavAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favourites);

        recyclerView = findViewById(R.id.gridView);
        favDB=new FavDB(getApplicationContext());

        LoadData();
    }

    private void LoadData() {

        SQLiteDatabase db = favDB.getReadableDatabase();
        Cursor cursor = favDB.select_all_favorite_list();

        //check empty list
        if (cursor.getCount() == 0){
            Toast.makeText(MyFavouritesActivity.this,"NO FAVOURITE ADDED!!",Toast.LENGTH_LONG).show();
            return;
        }

        favouriteList = new ArrayList<>();
        if (favouriteList != null) {
            favouriteList.clear();
        }


        try {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(FavDB.ITEM_TITLE));
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(FavDB.KEY_ID));
                @SuppressLint("Range") int image = Integer.parseInt(cursor.getString(cursor.getColumnIndex(FavDB.ITEM_IMAGE)));
                FavModel favItem = new FavModel(title, id, image);
                favouriteList.add(favItem);
                Log.d("Fav List =", String.valueOf(favouriteList));
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //adapter = new DeepRelaxAdapter(this,favouriteList);
        recyclerView.setAdapter(adapter);

        Log.d("TAG", "LoadData: " + favouriteList);

    }
}