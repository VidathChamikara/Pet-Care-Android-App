package com.example.madprojectdelta;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.madprojectdelta.models.Dog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class corner_view_post extends AppCompatActivity {

    TextView type,price,description,contactNo,email;
    ImageView imageView,logo;
    Button edit,remove;
    DatabaseReference dbRef;
    //Dog dog = new Dog();

    String dogID = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corner_view_post);

        logo = findViewById(R.id.app_logo_top);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(corner_view_post.this, Home.class);
                startActivity(intent);
            }
        });

        type = (TextView)findViewById(R.id.viewpost_type);
        price = (TextView)findViewById(R.id.view_post_price);
        description = (TextView)findViewById(R.id.view_post_desc);
        contactNo = (TextView)findViewById(R.id.view_post_phone);
        email = (TextView)findViewById(R.id.view_post_email);
        imageView = (ImageView)findViewById(R.id.view_post_image);

        dbRef = FirebaseDatabase.getInstance().getReference();

        dogID =  getIntent().getStringExtra("did");

        getDogDetails(dogID);

        edit = findViewById(R.id.view_post_edit);
        remove = findViewById(R.id.edit_post_cancel);

    }

    private void getDogDetails(String dogID) {
        //get userID
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();

        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child("Dog");

        dataRef.child(dogID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Dog dog = snapshot.getValue(Dog.class);

                    type.setText(dog.getType());
                    price.setText("Rs "+dog.getPrice().toString());
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

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(corner_view_post.this, corner_edit_post.class);
                intent.putExtra( "did", dogID);
                startActivity(intent);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();
                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Dog");
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(dogID)){
                            dbRef = FirebaseDatabase.getInstance().getReference().child("Dog").child(dogID);
                            dbRef.removeValue();


                            Toast.makeText(getApplicationContext(),"Data Deleted Successfully",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(corner_view_post.this, corner_myAds.class);
                            startActivity(intent);

                        }
                        else
                            Toast.makeText(getApplicationContext(),"No Source to Delete",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

        //bottom navigation bar begins


    }


}