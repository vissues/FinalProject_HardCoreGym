package com.example.hardcoregym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IfEmailNotVerified extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_if_email_not_verified);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        if(mUser.isEmailVerified()){
            Intent intent=new Intent(IfEmailNotVerified.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        if(!mUser.isEmailVerified()){
            Toast.makeText(IfEmailNotVerified.this, "Please verify your email!", Toast.LENGTH_SHORT).show();
        }
    }
}