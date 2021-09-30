package com.example.calmable;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class JournalFragment extends Fragment {


       public JournalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_journal, container, false);

        ArrayList<String> listOfNotes = new ArrayList<>();

        ListView listViewJournal  = (ListView) view.findViewById(R.id.listViewJournal);


        ArrayAdapter<String> listViewNoteAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                listOfNotes
        );


        listViewJournal.setAdapter(listViewNoteAdapter);


        listViewJournal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), NoteEditorActivity.class);
                intent.putExtra("noteId",i);

                Log.i("TAG", "btn create success");

                startActivity(intent);
            }
        });
        return view;



    }

}
