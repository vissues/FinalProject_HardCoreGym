package com.example.store;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hardcoregym.MainActivity;
import com.example.hardcoregym.R;

public class GoCart_AdminCategoryActivity extends AppCompatActivity {

    private Button LogoutBtn, CheckOrdersBtn, AddProductBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_cart_admin_category);



        LogoutBtn = findViewById(R.id.admin_logout_btn);
        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(GoCart_AdminCategoryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        CheckOrdersBtn = (Button) findViewById(R.id.check_orders_btn);
        CheckOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(GoCart_AdminCategoryActivity.this, GoCart_AdminNewOrdersActivity.class);
                startActivity(intent);
            }
        });

        AddProductBtn = findViewById(R.id.add_product_btn);
        AddProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GoCart_AdminCategoryActivity.this,GoCart_AdminActivity.class);
                startActivity(intent);
            }
        });



    }
}
