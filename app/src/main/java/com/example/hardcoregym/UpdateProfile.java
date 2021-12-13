package com.example.hardcoregym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateProfile extends AppCompatActivity {

    EditText UpdateFirstName, UpdateLastName, UpdatePhoneNo, UpdateDOB;
    String FirstName, LastName , email, PhoneNumber, DateofBirth;
    Button UpdateProfileBtn;
    TextView  updateprofilepicture_TV;
    ProgressBar progressbar_updateprofile;
    DatePickerDialog picker;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        progressbar_updateprofile=findViewById(R.id.progressbar_updateprofile);
        UpdateFirstName=findViewById(R.id.input_UpdateFirstName);
        UpdateLastName=findViewById(R.id.input_UpdateLastName);
        UpdatePhoneNo=findViewById(R.id.input_UpdatePhoneNo);
        UpdateDOB=findViewById(R.id.input_UpdateDOB);

        UpdateProfileBtn=findViewById(R.id.updateprofile_btn);
        updateprofilepicture_TV=findViewById(R.id.updateprofilepicture_TV);
        //updateemail_TV=findViewById(R.id.updateemail_TV);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        ShowProfile(mUser);

        updateprofilepicture_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateProfile.this, UploadProfilePicture.class);
                startActivity(intent);
                finish();
            }
        });

       /* updateemail_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateProfile.this, UpdateEmail.class);
                startActivity(intent);
                finish();
            }
        });*/


        UpdateDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                picker = new DatePickerDialog(UpdateProfile.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
                        UpdateDOB.setText(dayofmonth + "/" + (month+1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

        UpdateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateProfileMethod(mUser);
            }
        });
    }

    private void UpdateProfileMethod(FirebaseUser mUser) {
        String mobileRegex ="[6-9][0-9]{9}";
        Matcher mobilephoneMatcher;
        Pattern mobilePattern = Pattern.compile(mobileRegex);
        mobilephoneMatcher = mobilePattern.matcher(PhoneNumber);

        if(FirstName.isEmpty() && LastName.isEmpty() && DateofBirth.isEmpty() ){
            UpdateFirstName.setError("Required");
            UpdateLastName.setError("Required");
            UpdateDOB.setError("Required");
        }
        else{
            FirstName=UpdateFirstName.getText().toString();
            LastName=UpdateLastName.getText().toString();
            PhoneNumber=UpdatePhoneNo.getText().toString();
            DateofBirth=UpdateDOB.getText().toString();

            AppUser_Info write_information = new AppUser_Info(FirstName, LastName, email, PhoneNumber, DateofBirth);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("appuser");
            String UserID = mUser.getUid();

            email=mUser.getEmail();///*^!@#$%^&*()_)(*&^%#@!@#$%^&*()

            progressbar_updateprofile.setVisibility(View.VISIBLE);
            UpdateProfileBtn.setVisibility(View.INVISIBLE);
            UpdateProfileBtn.setEnabled(false);
            databaseReference.child(UserID).setValue(write_information).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        UserProfileChangeRequest UpdatedProfile = new UserProfileChangeRequest.Builder().build();
                        mUser.updateProfile(UpdatedProfile);
                        Toast.makeText(UpdateProfile.this, "Profile updated Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(UpdateProfile.this, Profile.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    }else {
                        try{
                            throw task.getException();
                        }catch (Exception e){
                            Toast.makeText(UpdateProfile.this, e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                    progressbar_updateprofile.setVisibility(View.INVISIBLE);
                    UpdateProfileBtn.setVisibility(View.VISIBLE);
                    UpdateProfileBtn.setEnabled(true);
                }
            });

        }
    }

    private void ShowProfile(FirebaseUser mUser) {
        String UIDofCurrentUser = mUser.getUid();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("appuser");

        //progressbar_updateprofile.setVisibility(View.VISIBLE);


        databaseReference.child(UIDofCurrentUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AppUser_Info read_information = snapshot.getValue(AppUser_Info.class);
                if(read_information!=null){
                    FirstName = read_information.FirstName;
                    LastName = read_information.LastName;
                    PhoneNumber = read_information.PhoneNumber;
                    DateofBirth=read_information.DateOfBirth;


                    UpdateFirstName.setText(FirstName);
                    UpdateLastName.setText(LastName);
                    UpdatePhoneNo.setText(PhoneNumber);
                    UpdateDOB.setText(DateofBirth);

                }else{
                    Toast.makeText(UpdateProfile.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
                //progressbar_updateprofile.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateProfile.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
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
            finish();
            overridePendingTransition(0,0);
        }else if(ID == R.id.Menu_update_profile){
            Intent intent = new Intent(UpdateProfile.this, UpdateProfile.class);
            startActivity(intent);
        } else if(ID == R.id.Menu_change_password) {
            //Intent intent = new Intent(Profile.this, ChangePassword.class);
            //startActivity(intent);
        }else if(ID == R.id.Menu_log_out) {
            mAuth.signOut();
            Toast.makeText(UpdateProfile.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateProfile.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(UpdateProfile.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}