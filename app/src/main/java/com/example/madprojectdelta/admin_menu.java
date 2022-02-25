package com.example.madprojectdelta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class admin_menu extends AppCompatActivity {

    Button carebtn, itemMng, toAdminpro, ordersbtn,logout;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        carebtn = findViewById(R.id.adminprofile_menu_ordersbtn);
        itemMng = findViewById(R.id.adminprofile_menu_items);
        toAdminpro = findViewById(R.id.adminprofile_menu_adsbtn);
        ordersbtn = findViewById(R.id.adminprofile_menu_profilebtn);

        logo = findViewById(R.id.app_logo_top);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_menu.this, admin_menu.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        ordersbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_menu.this, admin_orders.class);
                startActivity(intent);
            }
        });

        itemMng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_menu.this, admin_viewProduct.class);
                startActivity(intent);
            }
        });

        toAdminpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_menu.this, admin_account.class);
                startActivity(intent);
            }
        });

        carebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_menu.this, admin_organization_view.class);
                startActivity(intent);
            }
        });

    }

}
