package com.example.calmable;



import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {


    private Button stressHomeBtn, sleepyHomebtn,motivateHomeBtn, happyHomeBtn, breathHomeBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        //"I'm stressed" button
        stressHomeBtn = (Button) view.findViewById(R.id.stressHomeBtn);
        stressHomeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(),stressHome.class));

            }
        });

        //"I'm Sleepy" button
        sleepyHomebtn = (Button) view.findViewById(R.id.sleepyHomebtn);
        sleepyHomebtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(),sleepyHome.class));

            }
        });

        //"I wanna motivate" button
        motivateHomeBtn = (Button) view.findViewById(R.id.motivateHomeBtn);
        motivateHomeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(),motivateHome.class));

            }
        });

        //"I'm Happy" button
        happyHomeBtn = (Button) view.findViewById(R.id.happyHomeBtn);
        happyHomeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(),happyHome.class));

            }
        });

        //"Take a deep breath" button
        breathHomeBtn = (Button) view.findViewById(R.id.breathHomeBtn);
        breathHomeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(),breathHome.class));

            }
        });

        return view;

    }


}


