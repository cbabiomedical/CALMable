package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class LocationReminderActivity extends AppCompatActivity {

    ArrayList<String> listOfDisplayLocations;
    int arrSize;
    ArrayAdapter<String> arrayAdapter;

    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_reminder);

        listView = findViewById(R.id.lv1);

        // read txt file server data
        listOfDisplayLocations = new ArrayList<String>();

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(getCacheDir() + "/displayLocationTxt.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scanner.hasNext()) {
            listOfDisplayLocations.add(scanner.next());
        }

        arrSize = listOfDisplayLocations.size();
        Log.d("TAG", "txt locations data ---> : " + listOfDisplayLocations);
        Log.d("TAG", "locations array size ---> : " + arrSize);
        scanner.close();


        // display arraylist data in ListView
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfDisplayLocations);
        listView.setAdapter(arrayAdapter);

    }


    public void btnGoBack(View view) {
        startActivity(new Intent(getApplicationContext() , ProfileMain.class));
    }
}