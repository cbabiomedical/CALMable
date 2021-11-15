package com.example.calmable;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PopUpOne extends AppCompatDialogFragment {

    private PopUpOneListener listener;
    private TextView editPerson;
    private EditText editPlace;
    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> adapter;
    //List<Object> personList = new ArrayList<>();
    String[] personList = {"Amal","Anil","Ayeshika","Sunil","Saman"};

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

        //SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.example.calmable", Context.MODE_PRIVATE);
        //HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("people", null);

//        if (set == null) {
//            //personList.add("Write your day here!");
//        } else {
//            personList = new ArrayList(set);
//        }

        //initialize adapter
        adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,personList);

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
            }
        });

        return builder.create();
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
