package com.example.madprojectdelta;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.madprojectdelta.models.SessionManagement;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class admin_account extends AppCompatActivity {

    ImageView logo;
    Button logoutBtn;
    TextView uname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_account);

        //Initializing id's
        logo = findViewById(R.id.app_logo_top);
        logoutBtn = findViewById(R.id.admin_logoutbtn);
        uname = findViewById(R.id.view_username);

        SessionManagement sessionManagement = new SessionManagement(admin_account.this);
        String Uname = sessionManagement.getSession();
        uname.setText(Uname);


    }

    @Override
    protected void onResume() {
        super.onResume();

        //bottom navigation bar begins



        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_account.this, admin_menu.class);
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //remove session
                SessionManagement sessionManagement = new SessionManagement(admin_account.this);
                sessionManagement.removeSession();
                navigateToLogin();
                
            }
        });

    }

    public void navigateToLogin()
    {
        Intent intent = new Intent(admin_account.this, login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}