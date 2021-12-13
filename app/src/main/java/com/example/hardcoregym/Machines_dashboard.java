package com.example.hardcoregym;

import static com.example.hardcoregym.R.id.app_bar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Machines_dashboard extends AppCompatActivity {

    CardView Forearm_Machine;
    CardView Supporting_Machine;
    CardView Bicep_Curl_Machine;
    CardView Lat_Pulldown;
    CardView Peck_Deck_Machine;
    CardView Leg_Press_Machine;
    CardView Cable_Cross_Machine;
    CardView Olympia_Bench_Press_Machine;
    CardView Spinning_Cycle_Machine;
    CardView Treadmill_Machine;
    CardView Sitted_calf_Machine;
    CardView Leg_extension_Machine;
    CardView Smith_Machine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machines_dashboard);

        Forearm_Machine = findViewById(R.id.Forearm_Machine);
        Forearm_Machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Machines_dashboard.this, Forearm_Machine.class);
                startActivity(intent);
            }

        });

        Supporting_Machine = findViewById(R.id.Supporting_Machine);
        Supporting_Machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Machines_dashboard.this, Supporting_Machine.class);
                startActivity(intent);
            }

        });

        Bicep_Curl_Machine = findViewById(R.id.Bicep_Curl_Machine);
        Bicep_Curl_Machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Machines_dashboard.this, Bicep_Curl.class);
                startActivity(intent);
            }

        });

        Lat_Pulldown = findViewById(R.id.Lat_Pulldown);
        Lat_Pulldown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Machines_dashboard.this, Lat_Pull_Down.class);
                startActivity(intent);
            }
        });

        Peck_Deck_Machine = findViewById(R.id.Peck_Deck_Machine);
        Peck_Deck_Machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Machines_dashboard.this, Peck_Deck_Machine.class);
                startActivity(intent);
            }
        });

        Leg_Press_Machine = findViewById(R.id.Leg_Press_Machine);
        Leg_Press_Machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Machines_dashboard.this, Leg_Press_Machine.class);
                startActivity(intent);
            }
        });

        Cable_Cross_Machine = findViewById(R.id.Cable_Cross_Machine);
        Cable_Cross_Machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Machines_dashboard.this, Cable_Cross_Machine.class);
                startActivity(intent);
            }
        });

        Olympia_Bench_Press_Machine = findViewById(R.id.Olympia_Bench_Press_Machine);
        Olympia_Bench_Press_Machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Machines_dashboard.this, Olympia_Bench_Press.class);
                startActivity(intent);
            }
        });

        Spinning_Cycle_Machine = findViewById(R.id.Spinning_Cycle_Machine);
        Spinning_Cycle_Machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Machines_dashboard.this, Spinning_Cycle.class);
                startActivity(intent);
            }
        });

        Treadmill_Machine = findViewById(R.id.Treadmill_Machine);
        Treadmill_Machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Machines_dashboard.this, Treadmill.class);
                startActivity(intent);
            }
        });

        Leg_extension_Machine = findViewById(R.id.Leg_extension_Machine);
        Leg_extension_Machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Machines_dashboard.this, Leg_Extension_machine.class);
                startActivity(intent);
            }
        });

        Sitted_calf_Machine = findViewById(R.id.Sitted_calf_Machine);
        Sitted_calf_Machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Machines_dashboard.this, Sitted_calf_machine.class);
                startActivity(intent);
            }
        });

        Smith_Machine = findViewById(R.id.Smith_Machine);
        Smith_Machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Machines_dashboard.this, Smith_Machine.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = findViewById(app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

    }
}