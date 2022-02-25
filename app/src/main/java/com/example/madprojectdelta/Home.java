package com.example.madprojectdelta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Home extends AppCompatActivity {
    ImageButton btncare,btncorner,btnshop,btnprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btncorner = findViewById(R.id.btn_corner);
        btnprofile = findViewById(R.id.btn_profile);
        btnshop =findViewById(R.id.btn_shop);
        btncare = findViewById(R.id.btn_care);

    }

    @Override
    protected void onResume() {
        super.onResume();

        btnprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,profile_menu.class);
                startActivity(intent);
            }
        });

        btnshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,shop_show_products.class);
                startActivity(intent);

            }
        });

        btncorner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,corner_show_ads.class);
                startActivity(intent);

            }
        });

        btncare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,care_show_clinics.class);
                startActivity(intent);
            }
        });
    }
}