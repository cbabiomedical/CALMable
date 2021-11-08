package com.example.calmable;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class PopUpOne extends AppCompatDialogFragment {

    private PopUpOneListener listener;
    private EditText editPerson;
    private EditText editPlace;

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
