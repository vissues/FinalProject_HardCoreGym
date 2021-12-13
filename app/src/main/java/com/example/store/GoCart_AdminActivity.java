package com.example.store;

import static com.example.hardcoregym.R.id.app_bar;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hardcoregym.R;

public class GoCart_AdminActivity extends AppCompatActivity {

    LinearLayout linear1_category_layout,linear2_category_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_cart_admin);

        linear1_category_layout = findViewById(R.id.linear1_category_layout);
        linear1_category_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GoCart_AdminActivity.this, GoCart_AdminAddNewProductActivity.class);
                intent.putExtra("category", "Gym Supplements");
                startActivity(intent);

            }
        });

        linear2_category_layout=findViewById(R.id.linear2_category_layout);
        linear2_category_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GoCart_AdminActivity.this, GoCart_AdminAddNewProductActivity.class);
                intent.putExtra("category","Medicines");
                startActivity(intent);

            }
        });

        Toolbar toolbar = findViewById(app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}