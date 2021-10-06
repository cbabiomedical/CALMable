package com.example.calmable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class EditProfileActivity extends AppCompatActivity {

    private EditText name , email, gender, phoneNumber;
    private TextView userName;

    private Button btnChangePw;

    private FirebaseAuth mAuth;
    DatabaseReference myRef;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        TextView edChangePassword = (TextView) findViewById(R.id.changePassword);


        name = findViewById(R.id.edName);
        email = findViewById(R.id.edEmail);
        phoneNumber = findViewById(R.id.edPhoneNo);

        test();

        edChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileActivity.this, ChangePasswordActivity.class);
                startActivity(intent);

            }
        });
    }


    private void test() {


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String name = snapshot.child("fullName").getValue(String.class);
                String age = snapshot.child("age").getValue(String.class);
                String phone = snapshot.child("phoneNumber").getValue(String.class);
                String email = snapshot.child("email").getValue(String.class);
                String gender = snapshot.child("gender").getValue(String.class);


                Log.d("TAG", "onDataChange: check-------------" + name);
                Log.d("TAG", "onDataChange: check-------------" + age);
                Log.d("TAG", "onDataChange: check-------------" + phone);
                Log.d("TAG", "onDataChange: check-------------" + email);
                Log.d("TAG", "onDataChange: check-------------" + gender);


                final TextView displaynametextview = (TextView) findViewById(R.id.userName);
                final EditText fullNameTextView = (EditText) findViewById(R.id.edName);
                final EditText emailTextView =(EditText) findViewById(R.id.edEmail);
                final EditText phoneNumberTextView =(EditText) findViewById(R.id.edPhoneNo);

                displaynametextview.setText(name);
                fullNameTextView.setText(name);
                emailTextView.setText(email);
                phoneNumberTextView.setText(phone);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}