package com.example.madprojectdelta;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madprojectdelta.models.ProductItem;
import com.example.madprojectdelta.viewholders.ProductViewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class shop_show_products extends AppCompatActivity {

    FloatingActionButton btn_cart_prod;
    private DatabaseReference prRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_show_products);

        logo = findViewById(R.id.app_logo_top);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(shop_show_products.this, Home.class);
                startActivity(intent);
            }
        });

        //initializing
        prRef = FirebaseDatabase.getInstance().getReference().child("ProductItem");

        btn_cart_prod = findViewById(R.id.btn_cart_prod);
        recyclerView = findViewById(R.id.product_itemview);
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

        FirebaseRecyclerAdapter<ProductItem, ProductViewholder> adapter =
                new FirebaseRecyclerAdapter<ProductItem, ProductViewholder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewholder holder, final int i, @NonNull final ProductItem model)
                    {
                        if(model.getQty() > 0)
                        {
                            holder.pnameTxt.setText(model.getProductName());
                            holder.priceTxt.setText("Price: Rs." + model.getUnitPrice().toString());
                            holder.pdesTxt.setText("Description: " + model.getDescription());
                            Picasso.get().load(model.getImage()).into(holder.imageView);

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(shop_show_products.this, shop_view_product.class);
                                    intent.putExtra("itmID", getRef(i).getKey());
                                    startActivity(intent);

                                }
                            });
                        }
                        else
                        {
                            holder.pnameTxt.setText(model.getProductName());
                            holder.priceTxt.setText("Out of Stock");
                            holder.pdesTxt.setText("Description: " + model.getDescription());
                            Picasso.get().load(model.getImage()).into(holder.imageView);
                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                            Toast.makeText(getApplicationContext(), "Sorry.Product is Out of Stock" , Toast.LENGTH_SHORT).show();

                                }
                            });
                        }

                    }

                    @NonNull
                    @Override
                    public ProductViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent,false);

                        ProductViewholder holder = new ProductViewholder(view);

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

//        bottom navigation bar ends

        btn_cart_prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(shop_show_products.this,shop_view_cart.class);
                startActivity(intent);
            }
        });

    }
}