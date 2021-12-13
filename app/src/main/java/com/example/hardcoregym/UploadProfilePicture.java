package com.example.hardcoregym;

import static com.example.hardcoregym.R.id.app_bar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UploadProfilePicture extends AppCompatActivity {

    ProgressBar progressbar_upload;
    ImageView ImageView_ProfileDP;
    FirebaseAuth mAuth;
    StorageReference storageReference;
    FirebaseUser mUser;
    Button Upload_picture,Select_picture;
    int PICK_IMAGE_REQUEST=1;
    Uri uriImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_profile_picture);

        Upload_picture = findViewById(R.id.Upload_picture_btn);
        Select_picture = findViewById(R.id.select_picture_btn);
        progressbar_upload=findViewById(R.id.progressbar_upload);
        ImageView_ProfileDP = findViewById(R.id.ImageView_ProfileDP);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        storageReference= FirebaseStorage.getInstance().getReference("DisplayPics");
        Uri uri = mUser.getPhotoUrl();
        Picasso.get().load(uri).into(ImageView_ProfileDP);

        Select_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChoose();
            }
        });

        Upload_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectPictureToUpload();
            }
        });

        Toolbar toolbar = findViewById(app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

    }

    private void SelectPictureToUpload() {
        progressbar_upload.setVisibility(View.VISIBLE);
        Upload_picture.setVisibility(View.INVISIBLE);
        Upload_picture.setEnabled(false);
        Select_picture.setVisibility(View.INVISIBLE);
        Select_picture.setEnabled(false);
        if(uriImage!=null){
            StorageReference fileReference = storageReference.child(mAuth.getCurrentUser().getUid()+"."
                    +getFileExtension(uriImage));
            fileReference.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri  downloadUri = uri;
                            mUser=mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(downloadUri).build();
                            mUser.updateProfile(profileUpdates);
                        }
                    });

                    progressbar_upload.setVisibility(View.INVISIBLE);
                    Upload_picture.setVisibility(View.VISIBLE);
                    Upload_picture.setEnabled(true);
                    Select_picture.setVisibility(View.VISIBLE);
                    Select_picture.setEnabled(true);
                    Toast.makeText(UploadProfilePicture.this, "Profile picture updated successfully.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UploadProfilePicture.this, Profile.class);
                    startActivity(intent);
                    finish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressbar_upload.setVisibility(View.INVISIBLE);
                    Upload_picture.setVisibility(View.VISIBLE);
                    Upload_picture.setEnabled(true);
                    Select_picture.setVisibility(View.VISIBLE);
                    Select_picture.setEnabled(true);
                    Toast.makeText(UploadProfilePicture.this, "Something went wrong. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            progressbar_upload.setVisibility(View.INVISIBLE);
            Upload_picture.setVisibility(View.VISIBLE);
            Upload_picture.setEnabled(true);
            Select_picture.setVisibility(View.VISIBLE);
            Select_picture.setEnabled(true);
            Toast.makeText(UploadProfilePicture.this, "No file was selected", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver CR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(CR.getType(uri));
    }

    private void openFileChoose() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data.getData()!=null){
            uriImage = data.getData();
            ImageView_ProfileDP.setImageURI(uriImage);
        }
    }

    /*@Override
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
            Intent intent = new Intent(UploadProfilePicture.this, UpdateProfile.class);
            startActivity(intent);
            finish();
        } else if(ID == R.id.Menu_change_password) {
            //Intent intent = new Intent(Profile.this, ChangePassword.class);
            //startActivity(intent);
        }else if(ID == R.id.Menu_log_out) {
            mAuth.signOut();
            Toast.makeText(UploadProfilePicture.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UploadProfilePicture.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(UploadProfilePicture.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }*/
}