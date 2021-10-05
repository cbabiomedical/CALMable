package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

public class NoteEditorActivity extends AppCompatActivity {

    int noteId;

    private TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        EditText editTextNote = (EditText) findViewById(R.id.editTextTextMultiLine);
        TextView tvDate = (TextView) findViewById(R.id.tvDate);

        Date realDate = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String date = sdf.format(realDate);
        tvDate.setText(date);

        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);

        if (noteId != -1) {
            editTextNote.setText(JournalFragment.listOfNotes.get(noteId));
        } else {
            JournalFragment.listOfNotes.add("");
            noteId = JournalFragment.listOfNotes.size() - 1;
            JournalFragment.listViewNoteAdapter.notifyDataSetChanged();

        }


        editTextNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                JournalFragment.listOfNotes.set(noteId, String.valueOf(charSequence));
                JournalFragment.listViewNoteAdapter.notifyDataSetChanged();


                // permanent save notes
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.calmable" , Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet(JournalFragment.listOfNotes);
                sharedPreferences.edit().putStringSet("listOfNotes" , set).apply();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}