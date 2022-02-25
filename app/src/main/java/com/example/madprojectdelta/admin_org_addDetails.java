package com.example.madprojectdelta;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.madprojectdelta.models.DogCare;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class admin_org_addDetails extends AppCompatActivity {

    EditText txtClinickName, txtContactNo, txtAddress, txtCity, txtDescription, txtOwner;
    Button btnAdd;
    DatabaseReference dbRef;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_org_add_details);

        logo = findViewById(R.id.app_logo_top);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_org_addDetails.this, admin_menu.class);
                startActivity(intent);
            }
        });

        txtClinickName = findViewById(R.id.cName);
        txtContactNo = findViewById(R.id.ConNo);
        txtAddress = findViewById(R.id.Cadd);
        txtCity = findViewById(R.id.Ccity);
        txtDescription = findViewById(R.id.cDes);
        txtOwner = findViewById(R.id.cOwner);
        btnAdd = findViewById(R.id.woofadmin_org_update);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbRef = FirebaseDatabase.getInstance().getReference().child("DogCare");
                try{
                    if (TextUtils.isEmpty(txtClinickName.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter a Clinic Name...", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtContactNo.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a Contact Number...", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtAddress.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter an Address...", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtCity.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter a City...", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtDescription.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter a Description", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtOwner.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please Enter an Owner Name...", Toast.LENGTH_SHORT).show();
                    else{

                        if(checkphonenumber()) {
                            //Take inputs from the user and assigning them to this instance(clinic) of the DogCare...
                            DogCare clinic = new DogCare();
                            clinic.setClinicName(txtClinickName.getText().toString());
                            clinic.setContactNo(txtContactNo.getText().toString());
                            clinic.setAddress(txtAddress.getText().toString());
                            clinic.setCity(txtCity.getText().toString());
                            clinic.setDescription(txtDescription.getText().toString());
                            clinic.setOwnerName(txtOwner.getText().toString());


                            //Insert into the database
                            dbRef.push().setValue(clinic)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {


                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), "Data Saved Successfully...", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(admin_org_addDetails.this, admin_organization_view.class);

                                                startActivity(intent);

                                            } else {
                                                String msg = task.getException().toString();
                                                Toast.makeText(getApplicationContext(), "Error:" + msg, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }


                    }
                } catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(), "Invalid Contact Number...", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        //bottom navigation bar begins

    }

    public boolean checkphonenumber()
    {
        String phone = txtContactNo.getText().toString();

        if(phone.length() == 10)
        {
            return true;
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter valid phone number", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

}