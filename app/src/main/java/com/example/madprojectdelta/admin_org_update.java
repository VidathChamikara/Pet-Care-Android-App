package com.example.madprojectdelta;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.madprojectdelta.models.DogCare;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class admin_org_update extends AppCompatActivity {

    EditText txtClinic, txtConNo, txtAddress, txtCity, txtDescription, txtOwner;
    Button btnUpdate;
    DatabaseReference upDbRef;
    DogCare updateClinic;
    String org_id;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_org_update);

        logo = findViewById(R.id.app_logo_top);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_org_update.this, admin_menu.class);
                startActivity(intent);
            }
        });

        org_id = getIntent().getStringExtra("id");

        System.out.println("-------------"+org_id);

        txtClinic = findViewById(R.id.updateClinic);
        txtConNo = findViewById(R.id.updateConNo);
        txtAddress = findViewById(R.id.updateAddress);
        txtCity = findViewById(R.id.updateCity);
        txtDescription = findViewById(R.id.updateDescription);
        txtOwner = findViewById(R.id.updateOwner);
        btnUpdate = findViewById(R.id.woofadmin_org_update);

        updateClinic = new DogCare();


        upDbRef = FirebaseDatabase.getInstance().getReference().child("DogCare").child(org_id);

        upDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    DogCare care = snapshot.getValue(DogCare.class);

                    String clinic = care.getClinicName();
                    String contact = care.getContactNo();
                    String address = care.getAddress();
                    String city = care.getCity();
                    String description = care.getDescription();
                    String owner = care.getOwnerName();

                    txtClinic.setText(clinic);
                    txtConNo.setText(contact);
                    txtAddress.setText(address);
                    txtCity.setText(city);
                    txtDescription.setText(description);
                    txtOwner.setText(owner);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                upDbRef.child("clinicName").setValue(txtClinic.getText().toString());
                upDbRef.child("contactNo").setValue(txtConNo.getText().toString());
                upDbRef.child("address").setValue(txtAddress.getText().toString());
                upDbRef.child("city").setValue(txtCity.getText().toString());
                upDbRef.child("description").setValue(txtDescription.getText().toString());
                upDbRef.child("ownerName").setValue(txtOwner.getText().toString());

                Intent intent = new Intent(admin_org_update.this, admin_organization_view.class);
                Toast.makeText(getApplicationContext(), "Updated Successfully...", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        //bottom navigation bar begins

    }
}