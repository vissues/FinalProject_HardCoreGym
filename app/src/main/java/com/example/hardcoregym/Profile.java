package com.example.hardcoregym;

import static com.example.hardcoregym.R.id.app_bar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class Profile extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    //Toolbar toolbar;
    TextView welcome_text,show_first_name,show_last_name,show_email,show_phoneno,show_dob;
    String FirstName, LastName, Email, PhoneNumber, DateofBirth;
    ImageView Profile_Image, More_Profile_Settings;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        welcome_text=findViewById(R.id.welcome_text);
        show_first_name=findViewById(R.id.show_first_name);
        show_last_name=findViewById(R.id.show_last_name);
        show_email = findViewById(R.id.show_email);
        show_phoneno = findViewById(R.id.show_phoneno);
        show_dob = findViewById(R.id.show_DOB);
        Profile_Image=findViewById(R.id.Profile_image);

        //toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        Toolbar toolbar = findViewById(app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        mAuth=FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if(mUser==null){
            Toast.makeText(Profile.this, "Something went wrong. ", Toast.LENGTH_LONG).show();
        }else{
            ShowUserProfile(mUser);
        }

        Profile_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, UploadProfilePicture.class);
                startActivity(intent);
            }
        });


    }

    private void ShowUserProfile(FirebaseUser mUser) {
        String UserID = mUser.getUid();
        Profile_Image=findViewById(R.id.Profile_image);
        databaseReference= FirebaseDatabase.getInstance().getReference("appuser");
        databaseReference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AppUser_Info read_information = snapshot.getValue(AppUser_Info.class);
                if (read_information !=null){
                    Email=mUser.getEmail();
                    FirstName= read_information.FirstName;
                    LastName=read_information.LastName;
                    PhoneNumber=read_information.PhoneNumber;
                    DateofBirth=read_information.DateOfBirth;

                    welcome_text.setText("Welcome, " +FirstName+ "!");
                    show_first_name.setText(FirstName);
                    show_last_name.setText(LastName);
                    show_email.setText(Email);
                    show_phoneno.setText(PhoneNumber);
                    show_dob.setText(DateofBirth);

                    Uri uri = mUser.getPhotoUrl();
                    Picasso.get().load(uri).into(Profile_Image);
                    //@#$%^&*()@#$%^&*()@#$%^&*()
                    if (mUser.getPhotoUrl()==null){
                        Profile_Image.setImageResource(R.drawable.profile_image);
                    }//$%^&*()#$%^&*()@#$%^&*()

                }else{
                    Toast.makeText(Profile.this, "Something went wrong. ", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Something went wrong. ", Toast.LENGTH_LONG).show();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.common_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int ID = item.getItemId();
        if (ID == R.id.Menu_refresh){
            startActivity(getIntent());
            overridePendingTransition(0,0);
            finish();
        }else if(ID == R.id.Menu_update_profile){
            Intent intent = new Intent(Profile.this, UpdateProfile.class);
            startActivity(intent);
            finish();
        }else if(ID == R.id.Menu_change_password) {
            Intent intent = new Intent(Profile.this, ChangePassword.class);
            startActivity(intent);
            finish();
        }else if(ID == R.id.Menu_log_out) {
            mAuth.signOut();
            Toast.makeText(Profile.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Profile.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(Profile.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}