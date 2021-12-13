package com.example.hardcoregym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    TextView donot_haveanacc, forgotpassword;
    EditText input_email, input_password;
    Button login_btn;
    ImageView btn_google_logo,btn_phone_logo,ShowHidePwd;
    String email_pattern="[a-zA-Z0-9._]+@[a-z]+\\.+[a-z]+";
    ProgressBar progressbar_login;
    private static final String TAG="Login";

    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        donot_haveanacc=findViewById(R.id.donot_haveanacc);
        forgotpassword=findViewById(R.id.forgotpassword);
        input_email=findViewById(R.id.email);
        input_password=findViewById(R.id.password);
        login_btn=findViewById(R.id.mainlogin_btn);
        //btn_google_logo=findViewById(R.id.google_logo);
        //btn_phone_logo=findViewById(R.id.phone_logo);
        progressbar_login=findViewById(R.id.progressbar_login);
        //ShowHidePwd = findViewById(R.id.show_hide_pwd);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();


        /*ShowHidePwd.setImageResource(R.drawable.ic_hide_pwd);
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

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, ForgotPassword.class));
            }
        });

        donot_haveanacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Register.class));
                finish();
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PeformLogin();
            }
        });

        /*btn_google_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,SigninViaGoogle.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });


        btn_phone_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,SigninViaPhone.class);
                startActivity(intent);
            }
        });*/

    }

    private void PeformLogin() {
        String email= input_email.getText().toString();
        String password=input_password.getText().toString();

        if(email.isEmpty()){
            input_email.setError("Email cannot be empty.");
            input_email.requestFocus();
        }else if (!email.matches(email_pattern)){
            input_email.setError("Please enter valid Email address.");
            input_email.requestFocus();
        }else if (password.isEmpty()){
            input_password.setError("Required.");
            input_password.requestFocus();
        }else{
            login_btn.setVisibility(View.INVISIBLE);
            progressbar_login.setVisibility(View.VISIBLE);
            login_btn.setEnabled(false);

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                            progressbar_login.setVisibility(View.INVISIBLE);
                            login_btn.setVisibility(View.VISIBLE);
                            login_btn.setEnabled(true);
                            SendUserToNextActivity();

                    }
                    else{
                        Toast.makeText(Login.this,"Invalid email or password.",Toast.LENGTH_SHORT).show();
                        login_btn.setVisibility(View.VISIBLE);
                        progressbar_login.setVisibility(View.INVISIBLE);
                        login_btn.setEnabled(true);
                        try{
                            throw task.getException();
                        } catch (FirebaseAuthInvalidUserException e){
                            input_email.setError("User does not exist or is no longer valid. Please register again.");
                            input_email.requestFocus();
                        }catch (FirebaseAuthInvalidCredentialsException e){
                            input_email.setError("Invalid credentials. Kindly check and re-enter.");
                            input_email.requestFocus();
                        }
                        catch (Exception e){
                            Log.e(TAG, e.getMessage());
                            Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
    }

    private void SendUserToNextActivity() {
        Intent intent=new Intent(Login.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}