package com.example.hardcoregym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    //Button Logout;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    CardView Exercises, DietTips, Music, Machines, Membership, GoCart;
    ImageButton Profile_btn;
    TextView welcomeNAME;
    String FirstName;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        if(mUser.isEmailVerified()){
            Toast.makeText(MainActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
        }



        //Logout=findViewById(R.id.btn_logout);
        mAuth=FirebaseAuth.getInstance();

        Exercises=findViewById(R.id.go_to_Exercises);
        DietTips=findViewById(R.id.go_to_DietTips);
        Music=findViewById(R.id.go_to_Music);
        Machines=findViewById(R.id.go_to_Machines);
        Membership=findViewById(R.id.go_to_Membership);
        GoCart=findViewById(R.id.go_to_GoCart);
        Profile_btn=findViewById(R.id.Profile_btn);
        welcomeNAME=findViewById(R.id.welcomeNAME);



        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        String UserID = mUser.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference("appuser");
        databaseReference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AppUser_Info read_information = snapshot.getValue(AppUser_Info.class);
                if (read_information !=null){
                    FirstName= read_information.FirstName;
                    welcomeNAME.setText( FirstName+ " :)");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                logoutUser();
            }
        });*/

        Profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Profile.class);
                startActivity(intent);
            }
        });

        Exercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Exercise_dashboard.class);
                startActivity(intent);
            }
        });
        DietTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DietTips.class);
                startActivity(intent);
            }
        });
        Music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Music.class);
                startActivity(intent);
            }
        });
        Machines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Machines_dashboard.class);
                startActivity(intent);
            }
        });
        Membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Membership.class);
                startActivity(intent);
            }
        });
        GoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, com.example.store.GoCart_MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Email verification required");
        builder.setMessage("We strictly suggest you to verify your email now. A verification link has been sent to your email, for the same.");
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
    }

    private void logoutUser() {
        Intent intent = new Intent(MainActivity.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    //^%#&@(^*@&*@#$%^&*()@#$%^&*()@#$%^&*()@#$&*(#$%^&*(----------------------------#######################
    /*@Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (!mUser.isEmailVerified()) {
            mUser.sendEmailVerification();
            showAlertDialog();
        }
        if (mUser.isEmailVerified()) {
            Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
        }
    }*/
    //^%#&@(^*@&*@#$%^&*()@#$%^&*()@#$%^&*()@#$&*(#$%^&*(-----------------------------###########################

}