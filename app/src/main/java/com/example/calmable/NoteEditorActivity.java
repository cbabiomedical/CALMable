package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.calmable.databinding.FragmentJournalBinding;

public class NoteEditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        EditText editTextNote = (EditText) findViewById(R.id.editTextTextMultiLine);


        Intent intent = getIntent();
        int noteId = intent.getIntExtra("noteId", -1);


//        if (noteId != -1) {
//            editTextNote.setText(JournalFragment.notes.get(noteId));
//        }

    }
}