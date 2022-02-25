package com.example.madprojectdelta;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.madprojectdelta.models.Dog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class corner_viewad extends AppCompatActivity {

    TextView type,price,description,phone;
    ImageButton contactNo;
    ImageView imageView,logo;
    DatabaseReference dbRef;

    String dogID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corner_viewad);

        logo = findViewById(R.id.app_logo_top);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(corner_viewad.this, Home.class);
                startActivity(intent);
            }
        });

        type = (TextView) findViewById(R.id.viewpost_type);
        price = (TextView) findViewById(R.id.view_post_price);
        description = (TextView) findViewById(R.id.view_post_desc);
        phone = (TextView)findViewById(R.id.phone);

        contactNo = (ImageButton) findViewById(R.id.call);

        imageView = (ImageView)findViewById(R.id.view_post_image);

        dbRef = FirebaseDatabase.getInstance().getReference();

        dogID =  getIntent().getStringExtra("did");

        getDogDetails(dogID);




    }

    private void getDogDetails(String dogID) {
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child("Dog");

        dataRef.child(dogID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Dog dog = snapshot.getValue(Dog.class);

                    type.setText(dog.getType());
                    price.setText("Rs "+dog.getPrice().toString());
                    description.setText(dog.getDescription());
                    phone.setText(dog.getContactNo().toString());
                    Picasso.get().load(dog.getImage()).into(imageView);

                    contactNo.setOnClickListener(new View.OnClickListener(){

                        @Override
                        public void onClick(View arg0) {
                            String number=phone.getText().toString();
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:"+number));
                            startActivity(callIntent);
                        }

                    });


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
    }
}