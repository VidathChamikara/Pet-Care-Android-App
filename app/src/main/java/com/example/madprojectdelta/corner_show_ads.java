package com.example.madprojectdelta;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madprojectdelta.models.Dog;
import com.example.madprojectdelta.viewholders.CornerAdViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class corner_show_ads extends AppCompatActivity {

    DatabaseReference dbRef;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corner_show_ads);

        logo = findViewById(R.id.app_logo_top);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(corner_show_ads.this, Home.class);
                startActivity(intent);
            }
        });

        dbRef = FirebaseDatabase.getInstance().getReference().child("Dog");

        recyclerView = findViewById(R.id.ads_recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Dog> options =
                new FirebaseRecyclerOptions.Builder<Dog>()
                        .setQuery(dbRef, Dog.class).build();

        FirebaseRecyclerAdapter<Dog, CornerAdViewHolder> adapter = new FirebaseRecyclerAdapter<Dog, CornerAdViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CornerAdViewHolder woofCornerAdViewHolder, final int i, @NonNull final Dog dog) {
                woofCornerAdViewHolder.type.setText(dog.getType());
                woofCornerAdViewHolder.description.setText(dog.getDescription());
                woofCornerAdViewHolder.price.setText( "Rs."+String.valueOf(dog.getPrice()) );
                Picasso.get().load(dog.getImage()).into(woofCornerAdViewHolder.imageView);

                woofCornerAdViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(corner_show_ads.this,corner_viewad.class);
                        intent.putExtra( "did", getRef(i).getKey());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public CornerAdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.corner_ads_layout  ,parent, false);
                CornerAdViewHolder woofCornerAdViewHolder = new CornerAdViewHolder(view);
                return woofCornerAdViewHolder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onResume() {

        super.onResume();

        //bottom navigation bar begins

        //bottom navigation bar ends

    }
}

