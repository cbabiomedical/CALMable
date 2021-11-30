package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.Calendar;
import java.util.HashMap;

public class CalenderActivity extends AppCompatActivity {
    //initialize variable
    CustomCalendar customCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_activity);

        //assign variable
        customCalendar = findViewById(R.id.custom_calender);

        //initialize description hash map
        HashMap<Object, Property> descHashmap = new HashMap<>();

        //initialize default property
        Property defaultProperty = new Property();

        //initialize default resource
        defaultProperty.layoutResource = R.layout.default_view;

        //initialize and assign variable
        defaultProperty.dateTextViewResource = R.id.text_view;

        //put object and property
        descHashmap.put("default",defaultProperty);

        //for current date
        Property currentProperty = new Property();
        currentProperty.layoutResource = R.layout.current_view;
        currentProperty.dateTextViewResource = R.id.text_view;
        descHashmap.put("happy",currentProperty);

        //for happy mood
        Property happyProperty = new Property();
        happyProperty.layoutResource = R.layout.happy_view;
        happyProperty.dateTextViewResource = R.id.text_view;
        descHashmap.put("happy",happyProperty);

        //for awesome mood
        Property awesomeProperty = new Property();
        awesomeProperty.layoutResource = R.layout.awesome_view;
        awesomeProperty.dateTextViewResource = R.id.text_view;
        descHashmap.put("awesome",awesomeProperty);

        //for relaxed mood
        Property relaxedProperty = new Property();
        relaxedProperty.layoutResource = R.layout.relaxed_view;
        relaxedProperty.dateTextViewResource = R.id.text_view;
        descHashmap.put("relaxed",relaxedProperty);

        //for sleepy mood
        Property sleepyProperty = new Property();
        sleepyProperty.layoutResource = R.layout.sleepy_view;
        sleepyProperty.dateTextViewResource = R.id.text_view;
        descHashmap.put("sleepy",sleepyProperty);

        //for sad mood
        Property sadProperty = new Property();
        sadProperty.layoutResource = R.layout.sad_view;
        sadProperty.dateTextViewResource = R.id.text_view;
        descHashmap.put("sad",sadProperty);

        //set desc hashmap on custom calender
        customCalendar.setMapDescToProp(descHashmap);

        //initialize date hashmap
        HashMap<Integer,Object> dateHashMap = new HashMap<>();

        //initialize calender
        Calendar calendar = Calendar.getInstance();

        //put values
        dateHashMap.put(calendar.get(Calendar.DAY_OF_MONTH),"current");
        dateHashMap.put(1,"happy");
        dateHashMap.put(2,"awesome");
        dateHashMap.put(4,"sad");
        dateHashMap.put(10,"sleepy");
        dateHashMap.put(11,"relaxed");

        //set date
        customCalendar.setDate(calendar,dateHashMap);

    }
}