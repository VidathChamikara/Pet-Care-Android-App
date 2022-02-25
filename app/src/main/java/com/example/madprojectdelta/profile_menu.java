package com.example.madprojectdelta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile_menu extends AppCompatActivity {

    Button orders,profile,myAd;
    ImageView logo;
    TextView emaiUser, nameUser;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_menu);

        logo = findViewById(R.id.app_logo_top);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile_menu.this, Home.class);
                startActivity(intent);
            }
        });

        orders = findViewById(R.id.profile_menu_ordersbtn);
        myAd = findViewById(R.id.profile_menu_adsbtn);
        profile =findViewById(R.id.profile_menu_profilebtn);
        emaiUser = findViewById(R.id.email_menu);
        nameUser = findViewById(R.id.userneame_menu);

        //get user in auth
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        DatabaseReference refDB = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        refDB.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.hasChildren())
                {
                    nameUser.setText(snapshot.child("username").getValue().toString());
                    emaiUser.setText(snapshot.child("email").getValue().toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        //bottom navigation bar begins

        //bottom navigation bar ends

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile_menu.this, Cus_viewProfile.class);
                startActivity(intent);
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile_menu.this, shop_show_orders.class);
                startActivity(intent);
            }
        });

        myAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile_menu.this, corner_myAds.class);
                startActivity(intent);
            }
        });

    }

}