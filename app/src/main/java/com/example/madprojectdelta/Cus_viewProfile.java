package com.example.madprojectdelta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class Cus_viewProfile extends AppCompatActivity {
    Button toUpdateprofile, logoutBtn;
    FirebaseAuth mAuth;
    TextView uname, email, phone;
    String user_id;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_view_profile);

        logo = findViewById(R.id.app_logo_top);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cus_viewProfile.this,Home.class);
                startActivity(intent);
            }
        });
        toUpdateprofile = findViewById(R.id.viewEditOwner);
        logoutBtn = findViewById(R.id.btn_signOut);

        uname = findViewById(R.id.viewUsername);
        email = findViewById(R.id.viewEmail);
        phone = findViewById(R.id.viewPhone);

        //get user in auth
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
        {
            user_id = user.getUid();
        }
        //get user details
        DatabaseReference refDB= FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        refDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.hasChildren())
                {
                    uname.setText(snapshot.child("username").getValue().toString());
                    phone.setText(snapshot.child("phone").getValue().toString());
                    email.setText(snapshot.child("email").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                Intent intent = new Intent(Cus_viewProfile.this, login.class);
                Toast.makeText(getApplicationContext(), "Sucessfully Log out from the account", Toast.LENGTH_SHORT).show();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        toUpdateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cus_viewProfile.this,Cus_updateProfile.class);
                startActivity(intent);
            }
        });
    }
    public void navigateToActivityLogin()
    {
        Intent intent = new Intent(Cus_viewProfile.this, login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}