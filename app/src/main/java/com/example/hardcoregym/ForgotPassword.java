package com.example.hardcoregym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class ForgotPassword extends AppCompatActivity {

    EditText input_email;
    Button btn_resetpassword;
    ProgressBar progressbar_resetpasssword;
    FirebaseAuth mAuth;
    private static final String TAG="ForgotPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        input_email=findViewById(R.id.email);
        btn_resetpassword=findViewById(R.id.resetpassword_btn);
        progressbar_resetpasssword=findViewById(R.id.progressbar_resetpasssword);

        btn_resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String EmailID=input_email.getText().toString();
                if(TextUtils.isEmpty(EmailID)){
                    input_email.setError("Required");
                    input_email.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(EmailID).matches()){
                    input_email.setError("Please enter valid email");
                    input_email.requestFocus();
                }else{
                    progressbar_resetpasssword.setVisibility(View.VISIBLE);
                    btn_resetpassword.setVisibility(View.INVISIBLE);
                    btn_resetpassword.setEnabled(false);
                    resetPassword(EmailID);
                }

            }
        });

    }

    private void resetPassword(String EmailID) {
        mAuth=FirebaseAuth.getInstance();
        mAuth.sendPasswordResetEmail(EmailID).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPassword.this, "A link to reset password has been sent " +
                            "to your regsitered email", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ForgotPassword.this, Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }else{
                    try{
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e){
                        input_email.setError("User does not exist or is no longer valid. Please register again.");
                        input_email.requestFocus();
                    } catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(ForgotPassword.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(ForgotPassword.this, "Something went wrong. Please try again later.", Toast.LENGTH_LONG).show();
                }
                progressbar_resetpasssword.setVisibility(View.INVISIBLE);
            }
        });
    }
}