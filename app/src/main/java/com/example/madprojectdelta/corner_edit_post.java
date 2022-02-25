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

import com.example.madprojectdelta.models.Dog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.regex.Pattern;

public class corner_edit_post extends AppCompatActivity {

    EditText type, price, description, contactNo, email;
    Button save, cancel;
    ImageView imageView, logo;
    DatabaseReference dbRef;
    StorageReference storageReference;

    String dogID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corner_edit_post);

        logo = findViewById(R.id.app_logo_top);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(corner_edit_post.this, Home.class);
                startActivity(intent);
            }
        });

        type = (EditText) findViewById(R.id.phone);
        price = (EditText) findViewById(R.id.editTextTextPersonName5);
        description = (EditText) findViewById(R.id.uname);
        contactNo = (EditText) findViewById(R.id.editTextPhone2);
        email = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        imageView = (ImageView) findViewById(R.id.view_post_image);

        dbRef = FirebaseDatabase.getInstance().getReference();

        dogID = getIntent().getStringExtra("did");

        getDogDetails(dogID);

        save = findViewById(R.id.btnProdViewDel);
        cancel = findViewById(R.id.edit_post_cancel);
    }

    private void getDogDetails(String dogID) {
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child("Dog");

        dataRef.child(dogID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Dog dog = snapshot.getValue(Dog.class);

                    type.setText(dog.getType());
                    price.setText(dog.getPrice().toString());
                    description.setText(dog.getDescription());
                    contactNo.setText(dog.getContactNo().toString());
                    email.setText(dog.getEmail());
                    Picasso.get().load(dog.getImage()).into(imageView);
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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkemail() || checkphonenumber()) {
                    dbRef = FirebaseDatabase.getInstance().getReference();
                    dbRef.child("Dog").child(dogID).child("type").setValue(type.getText().toString().trim());
                    dbRef.child("Dog").child(dogID).child("price").setValue(Double.parseDouble(price.getText().toString()));
                    dbRef.child("Dog").child(dogID).child("description").setValue(description.getText().toString());
                    dbRef.child("Dog").child(dogID).child("contactNo").setValue(Integer.parseInt(contactNo.getText().toString()));
                    dbRef.child("Dog").child(dogID).child("email").setValue(email.getText().toString().trim());
                    Intent intent = new Intent(corner_edit_post.this, corner_view_post.class);
                    Toast.makeText(getApplicationContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(corner_edit_post.this, corner_myAds.class);
                startActivity(intent);
            }
        });

        //bottom navigation bar begins

    }

    public boolean checkphonenumber() {
        String phone = contactNo.getText().toString();

        if (phone.length() == 10) {
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "Please enter valid phone number", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public boolean checkemail() {
        String emailval = email.getText().toString();
        String EmalFormat = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)(\\.[A-Za-z]{2,})$";

        if (Pattern.compile(EmalFormat).matcher(emailval).matches()) {
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}