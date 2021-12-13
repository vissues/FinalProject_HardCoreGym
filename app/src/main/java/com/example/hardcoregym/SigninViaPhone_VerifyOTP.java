package com.example.hardcoregym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SigninViaPhone_VerifyOTP extends AppCompatActivity {

    EditText input_otp;
    TextView text_resendotp, otp_form_feedback;
    Button btn_verifyotp;
    ProgressBar progressbar_verifyotp;
    FirebaseAuth mAuth;
    String  mAuthVerificationId;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_via_phone_verify_otp);

        btn_verifyotp = findViewById(R.id.btn_verifyotp);
        //text_resendotp = findViewById(R.id.text_resendotp);
        input_otp=findViewById(R.id.input_OTP);
        progressbar_verifyotp=findViewById(R.id.progressbar_verifyotp);
        otp_form_feedback=findViewById(R.id.otp_form_feedback);

        mAuth=FirebaseAuth.getInstance();
        mAuthVerificationId = getIntent().getStringExtra("AuthCredentials");

        btn_verifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String otp = input_otp.getText().toString();

                if(otp.isEmpty()){
                    otp_form_feedback.setVisibility(View.VISIBLE);
                    otp_form_feedback.setText("Please enter valid OTP.");
                } else {

                    progressbar_verifyotp.setVisibility(View.VISIBLE);
                    btn_verifyotp.setEnabled(false);
                    btn_verifyotp.setVisibility(View.INVISIBLE);

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mAuthVerificationId, otp);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

        /*text_resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        SigninViaPhone_VerifyOTP.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            }
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(SigninViaPhone_VerifyOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onCodeSent(@NonNull String newbackendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                getotpbackend = newbackendotp;
                                Toast.makeText(SigninViaPhone_VerifyOTP.this, "OTP resend successfully.", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });*/

    }

    private void requestSMSpermission() {
        String permission = Manifest.permission.RECEIVE_SMS;
        int granted = ContextCompat.checkSelfPermission(this,permission);
        if (granted!= PackageManager.PERMISSION_GRANTED){
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this,permission_list,1);
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(SigninViaPhone_VerifyOTP.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendUserToHome();
                            // ...
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                otp_form_feedback.setVisibility(View.VISIBLE);
                                otp_form_feedback.setText("There was an error in verifying the OTP.");
                            }
                        }
                        progressbar_verifyotp.setVisibility(View.INVISIBLE);
                        btn_verifyotp.setEnabled(true);
                        btn_verifyotp.setVisibility(View.VISIBLE);
                    }
                });
    }

    public void sendUserToHome() {
        Intent homeIntent = new Intent(SigninViaPhone_VerifyOTP.this, MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }
}