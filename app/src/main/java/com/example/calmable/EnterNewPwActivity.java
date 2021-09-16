package com.example.calmable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EnterNewPwActivity extends AppCompatActivity {

    private EditText edNewPw1, edNewPw2;
    private Button changePwBtn;
    FirebaseUser user;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_new_pw);

        mAuth = FirebaseAuth.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();

        edNewPw1 =(EditText) findViewById(R.id.newPw1);
        edNewPw2 =(EditText) findViewById(R.id.newPw2);

        changePwBtn =(Button) findViewById(R.id.buttonChangePw);

        changePwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edNewPw1.getText().toString().isEmpty()) {
                    edNewPw1.setText("Required Field");
                    return;
                }

                if (edNewPw2.getText().toString().isEmpty()) {
                    edNewPw2.setError("Required Field");
                    return;
                }

                if(!edNewPw1.getText().toString().equals(edNewPw2.getText().toString())){
                    edNewPw1.setError("Password Do not Match");
                }


                user.updatePassword(edNewPw1.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EnterNewPwActivity.this, "Password Updated, \nLogin Again", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EnterNewPwActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });


    }
}