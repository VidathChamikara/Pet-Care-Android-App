package com.example.madprojectdelta;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madprojectdelta.models.Order;
import com.example.madprojectdelta.viewholders.OrdersViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class admin_orders extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference dbRef;
    ImageView logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orderview);

        logo = findViewById(R.id.app_logo_top);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_orders.this, admin_menu.class);
                startActivity(intent);
            }
        });

        dbRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        recyclerView = findViewById(R.id.showAdminOrdersView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Order> options = new FirebaseRecyclerOptions.Builder<Order>()
                .setQuery(dbRef, Order.class).build();

        FirebaseRecyclerAdapter<Order, OrdersViewHolder> adapter = new FirebaseRecyclerAdapter<Order, OrdersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final OrdersViewHolder adminViewHolder, final int i, @NonNull final Order order) {

                String status = order.getStatus();
                adminViewHolder.receiver.setText("Customer(Received): " + order.getName());
                adminViewHolder.address.setText("Address : " + order.getAddress());
                adminViewHolder.phone.setText("Phone: " + order.getPhone());
                adminViewHolder.price.setText("Total Amount: " + order.getTotalAmount());
                adminViewHolder.postal.setText("Postal Code: " + order.getPostalCode());
                adminViewHolder.date.setText("Date: " + order.getDateOrdered());
                adminViewHolder.status.setText("Status: " + status);


                if (order.getStatus().equals("Pending")) {

                    adminViewHolder.viewAllBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(admin_orders.this, admin_ordersViewItems.class);
                            intent.putExtra("OrdID", getRef(i).getKey());
                            startActivity(intent);
                        }
                    });


                    //update order status
                    adminViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View view) {


                            final String ordID = getRef(i).getKey();

                            CharSequence options[] = new CharSequence[]
                                    {
                                            "Confirm set Dilivered Order",
                                            "Cancel"
                                    };
                            AlertDialog.Builder builder = new AlertDialog.Builder(admin_orders.this);
                            builder.setTitle("Order Options");

                            builder.setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //admin click on the confirm
                                    if (i == 0) {

                                        //update order status
                                        final DatabaseReference dbUpdateRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(ordID);

                                        dbUpdateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                dbUpdateRef.child("status").setValue("Dilivered");
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                        //disable view Button
                                        adminViewHolder.viewAllBtn.setVisibility(view.GONE);

                                    }
                                }
                            });

                            //alert dialog finish - show dialog
                            builder.show();
                        }

                    });
                }
                else
                {
                    adminViewHolder.viewAllBtn.setText("Order on Dilivery Status.");
                }
            }

            @NonNull
            @Override
            public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_ordrer_layout,parent,false);

                return new OrdersViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

}