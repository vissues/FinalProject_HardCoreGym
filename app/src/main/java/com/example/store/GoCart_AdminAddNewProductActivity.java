package com.example.store;

import static com.example.hardcoregym.R.id.app_bar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hardcoregym.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class GoCart_AdminAddNewProductActivity extends AppCompatActivity {
    private String CategoryName, Description, Price, Pname, saveCurrentDate, saveCurrentTime;
    Button AddNewProductButton;
    ImageView InputProductImage;
    EditText InputProductName, InputProductDescription, InputProductPrice;
    private static final int GalleryPick = 1;
    Uri ImageUri;
    String productRandomKey, downloadImageUrl;
    StorageReference ProductImagesRef;
    DatabaseReference ProductsRef;
    ProgressBar loadingBar;
    TextView add_product_feedback;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_cart_admin_add_new_product);


        CategoryName = getIntent().getExtras().get("category").toString();
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");


        AddNewProductButton = findViewById(R.id.add_new_product);
        InputProductImage = findViewById(R.id.select_product_image);
        InputProductName = findViewById(R.id.product_name);
        InputProductDescription = findViewById(R.id.product_description);
        InputProductPrice = findViewById(R.id.product_price);
        loadingBar = findViewById(R.id.progressbar_addproduct);
        add_product_feedback = findViewById(R.id.add_product_feedback);

        Toolbar toolbar = findViewById(app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());


        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }
        });


        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ValidateProductData();
            }
        });
    }


    private void OpenGallery(){
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            InputProductImage.setImageURI(ImageUri);
        }
    }
    private void ValidateProductData() {
        Description = InputProductDescription.getText().toString();
        Price = InputProductPrice.getText().toString();
        Pname = InputProductName.getText().toString();
        if (ImageUri == null)
        {
            //Toast.makeText(this, "Product image is mandatory...", Toast.LENGTH_SHORT).show();
            add_product_feedback.setText("Product image is mandatory.");
            add_product_feedback.setVisibility(View.VISIBLE);
        }
        else if (TextUtils.isEmpty(Description))
        {
            //Toast.makeText(this, "Please write product description...", Toast.LENGTH_SHORT).show();
            add_product_feedback.setText("Product description is mandatory.");
            add_product_feedback.setVisibility(View.VISIBLE);
        }
        else if (TextUtils.isEmpty(Price))
        {
            //Toast.makeText(this, "Please write product Price...", Toast.LENGTH_SHORT).show();
            add_product_feedback.setText("Product Price is mandatory.");
            add_product_feedback.setVisibility(View.VISIBLE);
        }
        else if (TextUtils.isEmpty(Pname))
        {
            //Toast.makeText(this, "Please write product name...", Toast.LENGTH_SHORT).show();
            add_product_feedback.setText("Product Name is mandatory.");
            add_product_feedback.setVisibility(View.VISIBLE);
        }
        else
        {
            StoreProductInformation();
        }

    }
    private void StoreProductInformation()
    {
        AddNewProductButton.setVisibility(View.INVISIBLE);
        AddNewProductButton.setEnabled(false);
        add_product_feedback.setText("Dear Admin, please wait while we are adding the new product.");
        add_product_feedback.setVisibility(View.VISIBLE);
        loadingBar.setVisibility(View.VISIBLE);

        Calendar calendar = Calendar.getInstance();
        Pname = InputProductName.getText().toString();
        Description = InputProductDescription.getText().toString();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = Pname + "- "+ Description;

        final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(GoCart_AdminAddNewProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.setVisibility(View.INVISIBLE);
                AddNewProductButton.setVisibility(View.VISIBLE);
                AddNewProductButton.setEnabled(true);
                add_product_feedback.setText("Error: "+message);

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(GoCart_AdminAddNewProductActivity.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();

                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            //Toast.makeText(GoCart_AdminAddNewProductActivity.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();
                            add_product_feedback.setText("Stored the product image successfully.");
                            add_product_feedback.setVisibility(View.VISIBLE);
                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });

    }
    private void SaveProductInfoToDatabase()
    {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", Description);
        productMap.put("image", downloadImageUrl);
        productMap.put("category", CategoryName);
        productMap.put("price", Price);
        productMap.put("pname", Pname);

        ProductsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(GoCart_AdminAddNewProductActivity.this, GoCart_AdminCategoryActivity.class);
                            startActivity(intent);

                            loadingBar.setVisibility(View.INVISIBLE);
                            AddNewProductButton.setVisibility(View.VISIBLE);
                            AddNewProductButton.setEnabled(true);
                            add_product_feedback.setText("Product added successfully");
                            Toast.makeText(GoCart_AdminAddNewProductActivity.this, "Product is added successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.setVisibility(View.INVISIBLE);
                            AddNewProductButton.setVisibility(View.VISIBLE);
                            AddNewProductButton.setEnabled(true);
                            String message = task.getException().toString();
                            Toast.makeText(GoCart_AdminAddNewProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            add_product_feedback.setText("Error: "+message);
                        }
                    }
                });
    }
}
