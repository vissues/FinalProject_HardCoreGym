package com.example.hardcoregym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class SigninViaPhone extends AppCompatActivity {

    TextView login_form_feedback,input_countrycode;
    EditText input_mobno;
    Button btn_getotp;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ProgressBar LoginProgressBar;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_via_phone);

        input_mobno = findViewById(R.id.input_mobno);
        input_countrycode=findViewById(R.id.input_countrycode);
        btn_getotp = findViewById(R.id.btn_getotp);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        LoginProgressBar=findViewById(R.id.progressbar_sendotp);
        login_form_feedback=findViewById(R.id.login_form_feedback);

        btn_getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mob_no= input_mobno.getText().toString();
                String complete_phone_number = "+91" + mob_no;

                if(mob_no.isEmpty()){
                    login_form_feedback.setText("Please enter valid mobile number.");
                    login_form_feedback.setVisibility(View.VISIBLE);
                }else
                    LoginProgressBar.setVisibility(View.VISIBLE);
                    btn_getotp.setVisibility(View.INVISIBLE);
                    btn_getotp.setEnabled(false);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        complete_phone_number,
                        60,
                        TimeUnit.SECONDS,
                        SigninViaPhone.this,
                        mCallbacks
                );
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                login_form_feedback.setText("Verification Failed, please try again.");
                login_form_feedback.setVisibility(View.VISIBLE);
                LoginProgressBar.setVisibility(View.INVISIBLE);
                btn_getotp.setVisibility(View.VISIBLE);
                btn_getotp.setEnabled(true);
            }

            @Override
            public void onCodeSent(final String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                Intent otpIntent = new Intent(SigninViaPhone.this, SigninViaPhone_VerifyOTP.class);
                                otpIntent.putExtra("AuthCredentials", s);
                                startActivity(otpIntent);
                            }
                        },
                        10000);
        }
    };
}


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(SigninViaPhone.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendUserToHome();
                            // ...
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                login_form_feedback.setVisibility(View.VISIBLE);
                                login_form_feedback.setText("There was an error in verifying the OTP.");
                            }
                        }
                        LoginProgressBar.setVisibility(View.INVISIBLE);
                        btn_getotp.setEnabled(true);
                    }
                });
    }

    private void sendUserToHome() {
        Intent homeIntent = new Intent(SigninViaPhone.this, MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }

}