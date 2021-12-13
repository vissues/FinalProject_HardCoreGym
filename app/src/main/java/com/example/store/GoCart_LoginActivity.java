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

import com.example.store.Model.Users;
import com.example.store.Prevalent.Prevalent;
import com.example.hardcoregym.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class GoCart_LoginActivity extends AppCompatActivity {
    EditText InputPhoneNumber, InputPassword;
    Button LoginButton;
    ProgressBar progressbar_gocartlogin;
    TextView AdminLink, NotAdminLink,login_feedback;
    String parentDbName = "Users";
    CheckBox chkBoxRememberMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_cart_login);

        LoginButton = findViewById(R.id.login_btn);
        InputPassword =  findViewById(R.id.login_password_input);
        InputPhoneNumber = findViewById(R.id.login_phone_number_input);
        AdminLink = findViewById(R.id.admin_panel_link);
        NotAdminLink = findViewById(R.id.not_admin_panel_link);
        progressbar_gocartlogin=findViewById(R.id.progressbar_gocartlogin);
        login_feedback=findViewById(R.id.login_feedback);

        chkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_chkb);
        Paper.init(this);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LoginButton.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDbName = "Admins";
            }
        });
        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LoginButton.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentDbName = "Users";
            }
        });

    }
    private void LoginUser()
    {
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(phone))
        {
            InputPhoneNumber.setError("Phone Number required.");
            InputPhoneNumber.requestFocus();
            //Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            InputPassword.setError("Password required.");
            InputPassword.requestFocus();
            //Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {

            LoginButton.setVisibility(View.INVISIBLE);
            LoginButton.setEnabled(false);
            login_feedback.setText("Please wait, while we are checking the credentials.");
            login_feedback.setVisibility(View.VISIBLE);
            progressbar_gocartlogin.setVisibility(View.VISIBLE);

            AllowAccessToAccount(phone, password);
        }
    }
    private void AllowAccessToAccount(final String phone, final String password)
    {
        Paper.book().write(Prevalent.UserPhoneKey, phone);
        Paper.book().write(Prevalent.UserPasswordKey, password);

        /*if(chkBoxRememberMe.isChecked() )
        {
            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }else{
            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }*/
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(phone).exists()){

                    Users usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);
                    if (usersData.getPhone().equals(phone))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                            if(parentDbName.equals("Admins"))
                            {
                                Toast.makeText(GoCart_LoginActivity.this, "Welcome Admin!\nLogged in Successfully", Toast.LENGTH_SHORT).show();
                                LoginButton.setVisibility(View.VISIBLE);
                                LoginButton.setEnabled(true);
                                login_feedback.setVisibility(View.INVISIBLE);
                                progressbar_gocartlogin.setVisibility(View.INVISIBLE);

                                Intent intent = new Intent(GoCart_LoginActivity.this, GoCart_AdminCategoryActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else if (parentDbName.equals("Users")){
                                Toast.makeText(GoCart_LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                LoginButton.setVisibility(View.VISIBLE);
                                LoginButton.setEnabled(true);
                                login_feedback.setVisibility(View.INVISIBLE);
                                progressbar_gocartlogin.setVisibility(View.INVISIBLE);

                                Intent intent = new Intent(GoCart_LoginActivity.this, GoCart_HomeActivity.class);
                                Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);
                                finish();
                            }

                        }
                        else {
                            LoginButton.setVisibility(View.VISIBLE);
                            LoginButton.setEnabled(true);
                            login_feedback.setText("Password is incorrect");
                            login_feedback.setVisibility(View.VISIBLE);
                            progressbar_gocartlogin.setVisibility(View.INVISIBLE);
                            //Toast.makeText(GoCart_LoginActivity.this,"Password is incorrect",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    //Toast.makeText(GoCart_LoginActivity.this, "Account with this " + phone + " number do not exist.", Toast.LENGTH_SHORT).show();
                    LoginButton.setVisibility(View.VISIBLE);
                    LoginButton.setEnabled(true);
                    login_feedback.setText("Account with the number " + phone + "  does not exist.");
                    login_feedback.setVisibility(View.VISIBLE);
                    progressbar_gocartlogin.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
