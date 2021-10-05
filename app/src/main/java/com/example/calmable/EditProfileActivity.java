package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class EditProfileActivity extends AppCompatActivity {

    private Button btnChangePw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        TextView edChangePassword = (TextView) findViewById(R.id.changePassword);

        edChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (EditProfileActivity.this, ChangePasswordActivity.class);
                startActivity(intent);

            }
        });
    }
}