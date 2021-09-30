package com.example.calmable;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

    private TextView log,registerUser,  tvOccupation;
    private EditText editTextFullname, editTextAge, editTextEmail, editTextPassword;
    private ProgressBar progressBar;
    Dialog dialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        // Assign variables
        mAuth = FirebaseAuth.getInstance();

        log = (Button) findViewById(R.id.log);
        log.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        editTextFullname = (EditText) findViewById(R.id.fullName);
        editTextAge = (EditText) findViewById(R.id.age);
        editTextEmail = (EditText) findViewById(R.id.TextInputLayout1);
        editTextPassword = (EditText) findViewById(R.id.textInputLayout2);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Occupation
        //tvOccupation = findViewById(R.id.tvOccupation);

        //Occupation
        /*tvOccupation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // initialize dialog
                dialog = new Dialog(RegisterUser.this);

                // set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner);
                //set custom height and width
                dialog.getWindow().setLayout(650, 800);
                // set transference background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //show dialog
                dialog.show();

                //initialize and assign variable
                EditText editText = dialog.findViewById(R.id.edOccupation);
                ListView listView = dialog.findViewById(R.id.listView);


                // Initialize array adapter
                ArrayAdapter<String> adapterOccupation = new ArrayAdapter<>(RegisterUser.this, android.R.layout.simple_list_item_1, OccupationData.occupations);
                // set adapter
                listView.setAdapter(adapterOccupation);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        // filter array list
                        adapterOccupation.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //when item selected from list
                        //set deleted item on text view
                        tvOccupation.setText(adapterOccupation.getItem(i));

                        dialog.dismiss();
                    }
                });
            }
        });  */
        // end occupation


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                startActivity(new Intent(this,RegisterUser.class));
                break;
            case R.id.log:
                startActivity(new Intent(this, LoginUserActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;
        }
    }

    //Register details validation

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullname.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String phoneNumber = "";

        if(fullName.isEmpty()){
            editTextFullname.setError("Full Name is Required");
            editTextFullname.requestFocus();
            return;
        }
        if(age.isEmpty()){
            editTextAge.setError("Age is Required");
            editTextAge.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextEmail.setError("Email is Required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Email is Invalid");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is Required");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editTextPassword.setError("Minimum Password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user= new User(fullName,age,email,phoneNumber);

                            //startActivity(new Intent(RegisterUser.this, EnterPhoneActivity.class));

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        startActivity(new Intent(RegisterUser.this, EnterPhoneActivity.class));

                                        Toast.makeText(RegisterUser.this,"User has been registered successfully!",Toast.LENGTH_LONG)
                                                .show();

                                        //redirect to login layout
                                    }
                                    else {
                                        Toast.makeText(RegisterUser.this,"Registration Unsuccessful. Try Again!",Toast.LENGTH_LONG)
                                                .show();
                                    }
                                    progressBar.setVisibility(View.GONE);
                                }
                            });

                        }
                    }
                });

    }
}