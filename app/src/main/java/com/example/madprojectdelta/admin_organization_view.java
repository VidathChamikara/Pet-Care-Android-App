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

import com.example.madprojectdelta.models.DogCare;
import com.example.madprojectdelta.viewholders.org_view;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class admin_organization_view extends AppCompatActivity {

    FloatingActionButton add_org;
    private DatabaseReference orgRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_organization_view);

        logo = findViewById(R.id.app_logo_top);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_organization_view.this, admin_menu.class);
                startActivity(intent);
            }
        });

        add_org = findViewById(R.id.add_new_org);
        orgRef = FirebaseDatabase.getInstance().getReference().child("DogCare");

        add_org.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_organization_view.this,admin_org_addDetails.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.org_recycleview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<DogCare> options = new FirebaseRecyclerOptions.Builder<DogCare>().setQuery(orgRef, DogCare.class).build();

        FirebaseRecyclerAdapter<DogCare, org_view> adapter = new FirebaseRecyclerAdapter<DogCare, org_view>(options) {
            @Override
            protected void onBindViewHolder(@NonNull org_view holder, final int i, @NonNull final DogCare org) {
                holder.txtOrgName.setText("Organization:"+org.getClinicName());
                holder.txtConNo.setText("Contact Number:"+org.getContactNo());
                holder.txtLocation.setText("Address:"+org.getAddress());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(admin_organization_view.this, admin_org_details.class);
                        intent.putExtra("id", getRef(i).getKey());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public org_view onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_viewcard, parent, false);
                org_view holder = new org_view(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //bottom navigation bar begins

    }
}