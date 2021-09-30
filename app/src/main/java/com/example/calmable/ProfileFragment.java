package com.example.calmable;

import android.app.Activity;
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


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), EditProfileActivity.class);
                in.putExtra("some","some data");
                startActivity(in);
            }

        });


        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), LoginUserActivity.class);
//                in.putExtra("some","some data");
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
                startActivity(in);

            }

        });
        return view;
    }

}
