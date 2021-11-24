package com.example.calmable;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class PopUpOne extends AppCompatDialogFragment {

    private PopUpOneListener listener;
    private EditText editPerson;
    private EditText editPlace;
    AutoCompleteTextView autoCompleteTextView;
    FusedLocationProviderClient fusedLocationProviderClient;

    ArrayAdapter<String> adapter;
    ArrayList<Object> personList = new ArrayList<>(Arrays.asList("Amal","Anil","Ayeshika","Sunil","Saman","Mother","Father","Sister","Brother","Boss","Daughter","Son"));
    //ArrayList<Object> personList = new ArrayList<>(Arrays.asList(String.valueOf(editPerson)));
    // String[] personList = {"Amal","Anil","Ayeshika","Sunil","Saman"};

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_popupone,null);

        builder.setView(view)
                .setTitle("")
                .setNegativeButton("Skip", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String person = editPerson.getText().toString();
                        String place = editPlace.getText().toString();
                        listener.applyText(person,place);
                    }
                });

        editPerson = view.findViewById(R.id.edit_person);
        editPlace = view.findViewById(R.id.edit_place);
        autoCompleteTextView = view.findViewById(R.id.ac_text_view);

        //initialize fusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity().getApplicationContext());

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //when permission granted
            getLocation();
        } else {
            //when permission denied
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


        //SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.example.calmable", Context.MODE_PRIVATE);
        //HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("people", null);

//        if (set == null) {
//            //personList.add("Write your day here!");
//        } else {
//            personList = new ArrayList(set);
//        }


        //initialize adapter
        adapter = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,personList);

        //Get suggestion after the number of word types
        autoCompleteTextView.setThreshold(1);

        //Set adapter
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //set selected text on text view
                editPerson.setText(adapter.getItem(i));
                //personList.add(editPerson);
                Log.d("---------", String.valueOf(editPerson));
            }
        });

        return builder.create();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location!=null){
                    try {
                        //initialize geocoder
                        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                        //initialize address list
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        //set address
                        editPlace.setText(addresses.get(0).getAddressLine(0));

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (PopUpOneListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"Must implement");
        }

    }

    public interface PopUpOneListener{
        void applyText(String person,String place);
    }
}
