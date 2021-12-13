package com.example.hardcoregym;

import static com.example.hardcoregym.R.id.app_bar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.View;
import android.widget.ImageButton;

public class Exercise_dashboard extends AppCompatActivity {

    CardView beginner,intermediate,pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_dashboard);

        beginner = findViewById(R.id.Beginner);
        intermediate = findViewById(R.id.Intermediate);
        pro = findViewById(R.id.Professional);

        Toolbar toolbar = findViewById(app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        beginner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Exercise_dashboard.this, Exerbeginner.class);
                startActivity(intent);
            }
        });

        intermediate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Exercise_dashboard.this, Exerint.class);
                startActivity(intent);
            }
        });

        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Exercise_dashboard.this, Exerpro.class);
                startActivity(intent);
            }
        });


    }
}