package com.example.store;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hardcoregym.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class GoCart_RegisterActivity extends AppCompatActivity {
    Button CreateAccountButton;
    EditText InputName, InputPhoneNumber, InputPassword;
    ProgressBar progressbar_gocartregister;
    TextView register_feedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_cart_register);
        CreateAccountButton = findViewById(R.id.register_btn);
        InputName = findViewById(R.id.register_username_input);
        InputPassword = findViewById(R.id.register_password_input);
        InputPhoneNumber =  findViewById(R.id.register_phone_number_input);
        register_feedback=findViewById(R.id.register_feedback);
        progressbar_gocartregister = findViewById(R.id.progressbar_gocartregister);

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
    }
    private void CreateAccount(){
        String name = InputName.getText().toString();
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();
        if (TextUtils.isEmpty(name))
        {
            InputName.setError("Name required.");
            InputName.requestFocus();
        }
        else if (TextUtils.isEmpty(phone))
        {
            InputPhoneNumber.setError("Phone number required");
            InputPhoneNumber.requestFocus();
        }
        else if (TextUtils.isEmpty(password))
        {
            InputPassword.setError("Password required");
            InputPassword.requestFocus();
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {

            CreateAccountButton.setVisibility(View.INVISIBLE);
            CreateAccountButton.setEnabled(false);
            register_feedback.setVisibility(View.VISIBLE);
            register_feedback.setText("Please wait, while we are checking the credentials...");
            progressbar_gocartregister.setVisibility(View.VISIBLE);

            ValidatephoneNumber(name, phone, password);
        }

    }

    private void ValidatephoneNumber(final String name, final String phone,final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(phone).exists())){
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone", phone);
                    userdataMap.put("password", password);
                    userdataMap.put("name", name);
                    RootRef.child("Users").child(phone).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful())
                            {
                                Toast.makeText(GoCart_RegisterActivity.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();

                                CreateAccountButton.setVisibility(View.VISIBLE);
                                CreateAccountButton.setEnabled(true);
                                register_feedback.setVisibility(View.INVISIBLE);
                                progressbar_gocartregister.setVisibility(View.INVISIBLE);

                                Intent intent = new Intent(GoCart_RegisterActivity.this, GoCart_LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {

                                CreateAccountButton.setVisibility(View.VISIBLE);
                                CreateAccountButton.setEnabled(true);
                                register_feedback.setVisibility(View.VISIBLE);
                                register_feedback.setText("Network Error: Please try again after some time");
                                progressbar_gocartregister.setVisibility(View.INVISIBLE);

                                //Toast.makeText(GoCart_RegisterActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



                }
                else {
                    //Toast.makeText(GoCart_RegisterActivity.this, "This " + phone + " already exists.", Toast.LENGTH_SHORT).show();

                    CreateAccountButton.setVisibility(View.VISIBLE);
                    CreateAccountButton.setEnabled(true);
                    register_feedback.setVisibility(View.VISIBLE);
                    register_feedback.setText("This " + phone + " already exists. \nPlease try again using another phone number.");
                    progressbar_gocartregister.setVisibility(View.INVISIBLE);
                    Toast.makeText(GoCart_RegisterActivity.this, "This " + phone + " already exists.\nPlease try again using another phone number.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GoCart_RegisterActivity.this, GoCart_MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
