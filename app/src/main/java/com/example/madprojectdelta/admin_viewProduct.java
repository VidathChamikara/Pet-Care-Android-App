package com.example.madprojectdelta;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madprojectdelta.models.ProductItem;
import com.example.madprojectdelta.viewholders.AdminProductViewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class admin_viewProduct extends AppCompatActivity {


    TextView toViewProduct;
    Button addItem;
    private DatabaseReference prRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ImageView logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_product);

        logo = findViewById(R.id.app_logo_top);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_viewProduct.this, admin_menu.class);
                startActivity(intent);
            }
        });

        addItem = findViewById(R.id.AdminAddITem);

                //initializing
        prRef = FirebaseDatabase.getInstance().getReference().child("ProductItem");

        recyclerView = findViewById(R.id.recyclerView_Products);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<ProductItem> options =
                new FirebaseRecyclerOptions.Builder<ProductItem>()
                        .setQuery(prRef, ProductItem.class)
                        .build();

        FirebaseRecyclerAdapter<ProductItem, AdminProductViewholder> adapter =
                new FirebaseRecyclerAdapter<ProductItem, AdminProductViewholder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull AdminProductViewholder holder, final int i, @NonNull final ProductItem model)
                    {
                        holder.pnameTxt.setText(model.getProductName());
                        holder.priceTxt.setText("Price: Rs." + model.getUnitPrice().toString());
                        holder.pdesTxt.setText( model.getDescription());
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(admin_viewProduct.this, admin_selectedProductView.class);
                                intent.putExtra("itmID", getRef(i).getKey());
                                startActivity(intent);

                            }
                        });

                    }

                    @NonNull
                    @Override
                    public AdminProductViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_rows_for_rv, parent,false);

                        AdminProductViewholder holder = new AdminProductViewholder(view);

                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }


    @Override
    protected void onResume() {
        super.onResume();

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_viewProduct.this,admin_addItem.class);
                startActivity(intent);
            }
        });

        //bottom navigation bar begins

    }
}