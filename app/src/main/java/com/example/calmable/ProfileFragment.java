package com.example.calmable;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button settings = (Button) view.findViewById(R.id.settings);
        Button signOutBtn = (Button) view.findViewById(R.id.signOut);
        Button myDownloadsBtn = (Button) view.findViewById(R.id.myDownloadsBtn);
        Button myFavouritesBtn = (Button) view.findViewById(R.id.myFavouritesBtn);
        Button remindersBtn = (Button) view.findViewById(R.id.remindersBtn);
        Button calenderBtn = (Button) view.findViewById(R.id.calenderBtn);
        Button premiumBtn = (Button) view.findViewById(R.id.premiumBtn);
        Button rateUsBtn = (Button) view.findViewById(R.id.rateUsBtn);
        Button aboutAppBtn = (Button) view.findViewById(R.id.aboutAppBtn);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(in);
            }

        });

        myDownloadsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), MyDownloadsActivity.class);
                startActivity(in);
            }

        });

        myFavouritesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), MyFavouritesActivity.class);
                startActivity(in);
            }

        });

        remindersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), RemindersActivity.class);
                startActivity(in);
            }

        });

        calenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), CalenderActiivity.class);
                startActivity(in);
            }

        });

        premiumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), PremiumActivity.class);
                startActivity(in);
            }

        });

        rateUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), RateUsActivity.class);
                startActivity(in);
            }

        });

        aboutAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), AboutAppActivity.class);
                startActivity(in);
            }

        });

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), LoginUserActivity.class);
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
                startActivity(in);

            }

        });
        return view;
    }

}
