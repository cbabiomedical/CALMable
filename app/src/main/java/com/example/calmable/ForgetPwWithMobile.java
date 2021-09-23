package com.example.calmable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ForgetPwWithMobile extends AppCompatActivity {

    private Spinner spinner;
    private EditText editTextPhoneNo;

    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pw_with_mobile);

        // initialize
        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        editTextPhoneNo = findViewById(R.id.editTextPhone);

        findViewById(R.id.buttonResetPw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];

                String number = editTextPhoneNo.getText().toString().trim();


                if (number.isEmpty() || number.length() < 9) {
                    editTextPhoneNo.setError("Valid number is required");
                    editTextPhoneNo.requestFocus();
                    return;
                }

                String phoneNumber = "+" + code + number;


                // check phone number exists or not DB
                Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(number);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        // if phone number exists then call OTP to verify phone number
                        for (DataSnapshot data : snapshot.getChildren()) {
                            if (data.exists()) {

                                editTextPhoneNo.setError(null);

                                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

                                System.out.println("----------------------a-----------------------");
                                System.out.println("----------------------a-----------------------");
                                System.out.println("----------------------a-----------------------");
                                System.out.println("----------------------a-----------------------");

                                if (phoneNumber.equals(data)) {
                                    Intent intent = new Intent(getApplicationContext(), ForgetPwOtp.class);
                                    intent.putExtra("phoneNo", number);
                                    intent.putExtra("whatToDo", "updateData");

                                    System.out.println("-------------------b--------------------------");
                                    System.out.println("--------------------b-------------------------");
                                    System.out.println("--------------------b-------------------------");
                                    System.out.println("--------------------b-------------------------");

                                    Toast.makeText(ForgetPwWithMobile.this, "Verify Success!",Toast.LENGTH_LONG).show();
                                    startActivity(intent);
                                    finish();
                                }

                            } else {
                                Toast.makeText(ForgetPwWithMobile.this, "No such user exist!",Toast.LENGTH_LONG).show();
                                editTextPhoneNo.setError("No such user exist!");
                                editTextPhoneNo.requestFocus();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


//                Intent intent = new Intent(ForgetPwWithMobile.this, ForgetPwOtp.class);
//                intent.putExtra("phonenumber", phoneNumber);
//                startActivity(intent);

            }
        });
    }
}
