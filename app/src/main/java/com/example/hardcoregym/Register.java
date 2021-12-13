package com.example.hardcoregym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Register extends AppCompatActivity {
    TextView haveanacc;
    EditText input_email,input_password,input_conf_password,input_FirstName,input_LastName, input_DOB, input_PhoneNumber;
    DatePickerDialog picker;
    Button signup_btn;
    String email_pattern="[a-zA-Z0-9._]+@[a-z]+\\.+[a-z]+";
    ProgressBar progressbar_register;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    private static final String TAG="Register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        haveanacc=findViewById(R.id.haveanacc);
        input_FirstName=findViewById(R.id.FirstName);
        input_LastName=findViewById(R.id.LastName);
        input_PhoneNumber=findViewById(R.id.PhoneNumber);
        input_DOB=findViewById(R.id.DOB);
        input_email=findViewById(R.id.Email);
        input_password=findViewById(R.id.Password);
        input_conf_password=findViewById(R.id.conf_password);
        signup_btn=findViewById(R.id.signup_btn);
        progressbar_register=findViewById(R.id.progressbar_register);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        /*ImageView ShowHidePwd = findViewById(R.id.show_hide_pwd);
        ShowHidePwd.setImageResource(R.drawable.ic_hide_pwd);
        ShowHidePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input_password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    input_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ShowHidePwd.setImageResource(R.drawable.ic_hide_pwd);
                }else{
                    input_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ShowHidePwd.setImageResource(R.drawable.ic_show_pwd);
                }
            }
        });*/


        input_DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                picker = new DatePickerDialog(Register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
                        input_DOB.setText(dayofmonth + "/" + (month+1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });


        haveanacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,Login.class));
                finish();
            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformAuth();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("appuser");

    }

    private void PerformAuth() {
        String FirstName= input_FirstName.getText().toString();
        String LastName= input_LastName.getText().toString();
        String PhoneNumber=input_PhoneNumber.getText().toString();
        String DateOfBirth=input_DOB.getText().toString();
        String email= input_email.getText().toString();
        String password=input_password.getText().toString();
        String conf_password=input_conf_password.getText().toString();


        if(FirstName.isEmpty() && LastName.isEmpty() && email.isEmpty() && PhoneNumber.isEmpty() && DateOfBirth.isEmpty()){
            input_FirstName.setError("Required");
            input_LastName.setError("Required");
            input_PhoneNumber.setError("Required");
            input_DOB.setError("Required");
            input_email.setError("Required");
        }else if (!email.matches(email_pattern)){
            input_email.setError("Please enter valid Email address.");
            input_email.requestFocus();
        }else if (password.isEmpty()){
            input_password.setError("Required.");
            input_password.requestFocus();
        }else if (password.length()<8){
            input_password.setError("Password must have atleast 8 characters.");
            input_password.requestFocus();
        }else if(!conf_password.equals(password)){
            input_conf_password.setError("Passwords do not match.");
            input_conf_password.requestFocus();
        }
        else{
            signup_btn.setVisibility(View.INVISIBLE);
            progressbar_register.setVisibility(View.VISIBLE);
            signup_btn.setEnabled(false);

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        mUser = mAuth.getCurrentUser();
                        mUser.sendEmailVerification();
                        AppUser_Info information = new AppUser_Info(
                                FirstName,LastName,email, PhoneNumber,DateOfBirth
                        );
                        FirebaseDatabase.getInstance().getReference("appuser")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    progressbar_register.setVisibility(View.INVISIBLE);
                                    signup_btn.setVisibility(View.VISIBLE);
                                    signup_btn.setEnabled(true);
                                    SendUserToNextActivity();
                                    Toast.makeText(Register.this,"Registration successful. Please verify your email.",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(Register.this, "User registration failed. Please try again", Toast.LENGTH_SHORT).show();
                                    progressbar_register.setVisibility(View.INVISIBLE);
                                    signup_btn.setVisibility(View.VISIBLE);
                                    signup_btn.setEnabled(true);
                                }
                            }
                        });


                    }else{
                        progressbar_register.setVisibility(View.INVISIBLE);
                        signup_btn.setVisibility(View.VISIBLE);
                        signup_btn.setEnabled(true);
                        try{
                            throw task.getException();
                        }catch (FirebaseAuthInvalidUserException e){
                        }catch (FirebaseAuthInvalidCredentialsException e){
                            input_email.setError("Your email is invalid or already in use.");
                            input_email.requestFocus();
                        } catch (FirebaseAuthUserCollisionException e){
                            input_email.setError("User is already registered with this email.");
                            input_email.requestFocus();
                        }catch (Exception e){
                            Log.e(TAG, e.getMessage());
                            Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        progressbar_register.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }


    private void SendUserToNextActivity() {
        Intent intent=new Intent(Register.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}