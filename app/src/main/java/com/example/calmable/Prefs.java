package com.example.calmable;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.Calendar;

public class Prefs {
    private SharedPreferences preferences;

    public Prefs(Activity activity){
        this.preferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }


    public void setDate(long milliseconds){
        preferences.edit().putLong("seconds", 0);
    }


    public void setSessions(int session){
        preferences.edit().putInt("sessions", session).apply();
    }

    public int getSessions(){
        return preferences.getInt("sessions", 0);
    }

    public void setBreaths(int breaths){
        preferences.edit().putInt("breaths", breaths).apply();
    }

    public int getBreaths(){
        return preferences.getInt("breaths", 0);
    }

}

